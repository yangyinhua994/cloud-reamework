package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.ApiDesc;
import com.example.convert.SensorParamConvert;
import com.example.dto.SensorParamDTO;
import com.example.entity.SensorParam;
import com.example.response.Response;
import com.example.service.SensorParamService;
import com.example.vo.SensorParamVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 传感器参数管理接口
 */
@RestController
@RequestMapping("/iot/sensor/param")
@Validated
@ApiDesc("传感器参数管理接口")
public class SensorParamController extends BaseController<SensorParam, SensorParamDTO, SensorParamVO, SensorParamService, SensorParamConvert> {

    public SensorParamController(SensorParamService service, SensorParamConvert convert) {
        super(service, convert);
    }

    @Override
    protected void preAddList(List<SensorParamDTO> dtoList) {
        super.getService().preAddList(dtoList);
    }

    @Override
    protected void postAddList(List<SensorParam> sensorParams) {
        super.getService().postAddList(sensorParams);
    }

    @Override
    protected void preUpdate(SensorParamDTO dto) {
        super.getService().preUpdate(dto);
    }

    @Override
    protected void postUpdate(SensorParam entity) {
        super.getService().postUpdate(entity);
    }

    @Override
    protected List<SensorParamVO> listData(SensorParamDTO dto) {
        return super.getService().listData(dto);
    }

    @Override
    protected Page<SensorParamVO> pageData(SensorParamDTO dto) {
        return super.getService().pageData(dto);
    }

    @Override
    protected void preReturn(List<SensorParamVO> sensorParamVOS) {
        super.getService().preReturn(sensorParamVOS);
    }

    /**
     * 下载传感器参数导入excel模板
     */
    @GetMapping("/excel/dowload")
    @ApiDesc("下载传感器参数导入excel模板")
    public void excelDownload(HttpServletResponse servlet) {
        super.getService().excelDownload(servlet);
    }

    /**
     * excel导入传感器参数
     */
    @PostMapping("/excel/import")
    @ApiDesc("excel导入传感器参数")
    public void importComponent(MultipartFile file) {
        super.getService().importComponent(file);
    }

}
