package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.SupplierDTO;
import com.example.entity.Supplier;

public interface SupplierService extends IService<Supplier> {
    IPage<Supplier> pageList(SupplierDTO supplierDTO);
}
