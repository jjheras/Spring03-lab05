package com.example.CarRegistry.service;

import com.example.CarRegistry.controller.dto.BrandDTO;

import java.util.List;

public interface BrandService {
    List<BrandDTO> getAllBrands();
    void addBrand(BrandDTO brandDto);
    void deleteBrandById(Integer id);
    BrandDTO getBrandById(Integer id);
    void updateBrand(Integer id, BrandDTO brandDto);
}
