package com.example.dto;

import com.alibaba.fastjson.JSONObject;
import com.example.utils.ObjectUtils;
import com.example.utils.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class DatabaseBaseDTO {

    private JSONObject payload;
    private JSONObject before;
    private JSONObject after;
    private JSONObject source;
    private String tableName;

    public static DatabaseBaseDTO parse(String json) {
        DatabaseBaseDTO databaseBaseDTO = new DatabaseBaseDTO();
        try {
            if (StringUtils.isBlank(json)) {
                return databaseBaseDTO;
            }
            JSONObject valueObject = JSONObject.parseObject(json);
            if (ObjectUtils.isNotEmpty(valueObject)) {
                JSONObject payload = valueObject.getJSONObject("payload");
                if (ObjectUtils.isNotEmpty(payload)) {
                    JSONObject before = payload.getJSONObject("before");
                    JSONObject after = payload.getJSONObject("after");
                    JSONObject source = payload.getJSONObject("source");
                    if (ObjectUtils.isNotEmpty(source)) {
                        String tableName = source.getString("table");
                        databaseBaseDTO.setTableName(tableName);
                    }
                    databaseBaseDTO.setPayload(payload);
                    databaseBaseDTO.setBefore(before);
                    databaseBaseDTO.setAfter(after);
                    databaseBaseDTO.setSource(source);
                }
            }
            return databaseBaseDTO;
        } catch (Exception e) {
            log.debug("解析数据失败 json = {}", json);
            return databaseBaseDTO;
        }

    }

}
