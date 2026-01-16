package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.SensorParamDTO;
import com.example.entity.SensorParam;
import com.example.vo.SensorParamVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SensorParamService extends BaseService<SensorParam> {

    void preAddList(List<SensorParamDTO> dtoList);

    void postAddList(List<SensorParam> sensorParams);

    void excelDownload(HttpServletResponse servlet);

    void importComponent(MultipartFile file);

    void preUpdate(SensorParamDTO dto);

    void postUpdate(SensorParam entity);

    List<SensorParamVO> listData(SensorParamDTO dto);

    Page<SensorParamVO> pageData(SensorParamDTO dto);

    void preReturn(List<SensorParamVO> sensorParamVOS);
}
