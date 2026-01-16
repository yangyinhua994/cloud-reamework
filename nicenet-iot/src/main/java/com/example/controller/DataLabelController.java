package com.example.controller;

import com.example.annotation.ApiDesc;
import com.example.convert.DataLabelConvert;
import com.example.dto.DataLabelDTO;
import com.example.entity.DataLabel;
import com.example.service.DataLabelService;
import com.example.vo.DataLabelVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据打标管理接口
 */
@RestController
@RequestMapping("/iot/data/label")
@Validated
@ApiDesc("数据打标管理接口")
public class DataLabelController extends BaseController<DataLabel, DataLabelDTO, DataLabelVO, DataLabelService, DataLabelConvert> {

    public DataLabelController(DataLabelService service, DataLabelConvert convert) {
        super(service, convert);
    }

    @Override
    protected void preAddList(List<DataLabelDTO> dtoList) {
        super.getService().preAddList(dtoList);
    }

}
