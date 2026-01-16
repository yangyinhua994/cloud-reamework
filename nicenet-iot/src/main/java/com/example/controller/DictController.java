package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.annotation.ApiDesc;
import com.example.convert.DictConvert;
import com.example.dto.DictDTO;
import com.example.entity.Dict;
import com.example.enums.DictTypeEnum;
import com.example.groups.AddUnit;
import com.example.response.Response;
import com.example.service.DictService;
import com.example.vo.DictVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

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

    @Override
    protected void preList(DictDTO dto, LambdaQueryWrapper<Dict> queryWrapper) {
        super.getService().preList(dto, queryWrapper);
    }

    @Override
    protected void prePageList(DictDTO dto, LambdaQueryWrapper<Dict> queryWrapper) {
        preList(dto, queryWrapper);
    }

    @PostMapping("/add/unit")
    @ApiDesc("新增字典单位")
    public Response<DictVO> addUnit(@RequestBody @Validated(AddUnit.class) DictDTO dto) {
        dto.setDictType(DictTypeEnum.PHYSICAL_UNIT.getCode());
        return Response.success(super.getService().add(dto));
    }

    @PostMapping("/add/dataType")
    @ApiDesc("新增字典数据类型")
    public Response<DictVO> addDataType(@RequestBody @Validated(AddUnit.class) DictDTO dto) {
        dto.setDictType(DictTypeEnum.DATA_TYPE.getCode());
        return Response.success(super.getService().add(dto));
    }

    /**
     * 查询字典单位列表
     */
    @GetMapping("/query/unit")
    @ApiDesc("查询字典单位列表")
    public Response<List<DictVO>> queryUnit() {
        DictDTO dictDTO = DictDTO.builder().dictType(DictTypeEnum.PHYSICAL_UNIT.getCode()).build();
        return super.list(dictDTO);
    }

    /**
     * 查询字典数据类型列表
     */
    @GetMapping("/query/dataType")
    @ApiDesc("查询字典数据类型列表")
    public Response<List<DictVO>> queryDataType() {
        DictDTO dictDTO = DictDTO.builder().dictType(DictTypeEnum.DATA_TYPE.getCode()).build();
        return super.list(dictDTO);
    }

    @PostConstruct
    public void updateDictMap() {
        super.getService().updateDictMap();
    }

    @Override
    protected void postChange() {
        super.getService().updateDictMap();
    }
}
