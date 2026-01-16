package com.example.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.nacos.common.packagescan.resource.ClassPathResource;
import com.alibaba.nacos.common.packagescan.resource.Resource;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.listener.ExcelReadListener;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class ExcelUtils {

    public static final String excelPath = "excel";

    public static final String componentExcelTemplateName = "部件信息导入模板.xlsx";
    public static final String componentExcelTemplatePath = excelPath + File.separator + componentExcelTemplateName;

    public static final String sensorParamExcelTemplateName = "传感器参数信息导入.xlsx";
    public static final String sensorParamExcelTemplatePath = excelPath + File.separator + sensorParamExcelTemplateName;


    public static <T> List<T> readExcel(MultipartFile file, Class<T> clazz) {
        return readExcel(file, clazz, 0);
    }

    public static <T> List<T> readExcel(MultipartFile file, Class<T> clazz, Integer sheetNo) {
        return readExcel(file, clazz, sheetNo, new ExcelReadListener<>());
    }

    public static <T> List<T> readExcel(MultipartFile file, Class<T> clazz, Integer sheetNo, ExcelReadListener<T> excelReadListener) {
        try {
            InputStream inputStream = file.getInputStream();
            return readExcel(inputStream, clazz, sheetNo, excelReadListener);
        } catch (IOException e) {
            throw new RuntimeException("获取文件流失败");
        }
    }

    public static <T> List<T> readExcel(InputStream fileInputStream, Class<T> clazz, Integer sheetNo, ExcelReadListener<T> excelReadListener) {
        return EasyExcel.read(fileInputStream, clazz, excelReadListener)
                .extraRead(CellExtraTypeEnum.MERGE)
                .sheet(sheetNo)
                .doReadSync();
    }

    public static void downloadComponentExcelTemplate(HttpServletResponse response) {
        excelDownload(response, componentExcelTemplatePath, componentExcelTemplateName);
    }

    public static void downloadSensorParamExcelTemplate(HttpServletResponse response) {
        excelDownload(response, sensorParamExcelTemplatePath, sensorParamExcelTemplateName);
    }

    public static void excelDownload(HttpServletResponse response, String filePath, String fileName) {
        if (ObjectUtils.isEmpty(response) || StringUtils.isExistsBlank(filePath, fileName)) {
            ApiException.error(ResponseMessageEnum.PARAM_ERROR);
        }
        Resource resource = new ClassPathResource(filePath);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            ApiException.error(ResponseMessageEnum.FILE_NOT_EXIST);
        }
        excelDownload(response, inputStream, fileName);
    }

    public static void excelDownload(HttpServletResponse response, InputStream inputStream, String fileName) {
        if (StringUtils.isBlank(fileName) || ObjectUtils.isAllEmpty(inputStream, response)) {
            return;
        }

        try {
            String utf8 = StandardCharsets.UTF_8.toString();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding(utf8);
            String encodeFileName = URLEncoder.encode(fileName, utf8).replace("+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + encodeFileName);

            try (OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
            ApiException.error(ResponseMessageEnum.FILE_DOWNLOAD_ERROR);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.warn("关闭输入流失败", e);
            }
        }
    }
}
