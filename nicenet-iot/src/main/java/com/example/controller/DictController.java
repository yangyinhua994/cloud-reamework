package com.example.controller;

import com.example.annotation.ApiDesc;
import com.example.convert.DictConvert;
import com.example.dto.DictDTO;
import com.example.entity.Dict;
import com.example.service.DictService;
import com.example.vo.DictVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 字典管理接口
 */
@RestController
@RequestMapping("/iot/dict")
@Validated
@ApiDesc("字典管理接口")
public class DictController extends BaseController<Dict, DictDTO, DictVO, DictService, DictConvert> {

    public DictController(DictService service, DictConvert convert) {
        super(service, convert);
    }

    @PostConstruct
    public void updateDictMap() {
        super.getService().updateDictMap();
    }

    @Override
    protected void onChange() {
        super.getService().updateDictMap();
    }
}
