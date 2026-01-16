package com.example.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.utils.CollectionUtils;
import com.example.utils.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Data
public class ExcelReadListener<T> implements ReadListener<T> {
    private List<T> dataList = new ArrayList<>();

    private Map<Integer, String> headMap = new HashMap<>();

    public ExcelReadListener() {
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        dataList.add(t);
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        headMap.forEach((key, value) -> {
            Integer columnIndex = value.getColumnIndex();
            String stringValue = value.getStringValue();
            this.headMap.put(columnIndex, stringValue);
        });
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }

        // 获取合并单元格的起始单元格数据
        Integer firstRowIndex = extra.getFirstRowIndex();
        Integer lastRowIndex = extra.getLastRowIndex();
        Integer firstColumnIndex = extra.getFirstColumnIndex();
        String excelPropertyName = headMap.get(firstColumnIndex);
        if (StringUtils.isEmpty(excelPropertyName)) {
            return;
        }

        T t = dataList.get(firstRowIndex - 1);
        Field[] fields = t.getClass().getDeclaredFields();

        // 查找对应的字段
        for (Field field : fields) {
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null && Objects.equals(annotation.value()[0], excelPropertyName)) {
                List<T> ts = dataList.subList(firstRowIndex, lastRowIndex);
                for (T t1 : ts) {
                    Field[] t1Field = t1.getClass().getDeclaredFields();
                    for (Field field1 : t1Field) {
                        ExcelProperty annotation1 = field1.getAnnotation(ExcelProperty.class);
                        if (Objects.equals(annotation.value()[0], annotation1.value()[0])) {
                            field.setAccessible(true);
                            field1.setAccessible(true);
                            try {
                                Object o = field.get(t);
                                field1.set(t1, o);
                            } catch (Exception e) {
                                log.error(ResponseMessageEnum.COPY_EXCEL_DATA_ERROR.getMessage(), e);
                                ApiException.error(ResponseMessageEnum.COPY_EXCEL_DATA_ERROR);
                            }
                        }
                    }
                }
            }
        }

    }

}