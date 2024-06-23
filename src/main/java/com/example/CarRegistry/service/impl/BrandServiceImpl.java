package com.example.CarRegistry.service.impl;

import com.example.CarRegistry.controller.dto.BrandDTO;
import com.example.CarRegistry.controller.dto.CarDTO;
import com.example.CarRegistry.controller.mapper.BrandMapper;
import com.example.CarRegistry.repository.BrandRepository;
import com.example.CarRegistry.repository.entity.BrandEntity;
import com.example.CarRegistry.repository.entity.CarEntity;
import com.example.CarRegistry.repository.mapper.BrandEntityMapper;
import com.example.CarRegistry.service.BrandService;
import com.example.CarRegistry.service.model.Brand;
import com.example.CarRegistry.service.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private static final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandEntityMapper brandEntityMapper;
    @Autowired
    private BrandMapper brandMapper;
    // Método para obtener todas las marcas
    @Override
    public List<BrandDTO> getAllBrands() {
        List<BrandEntity> brands = brandRepository.findAll();
        if(brands.isEmpty()){
            log.warn("No se encuentran marcas en BBDD");
            throw new RuntimeException("No se encuentran marcas en BBDD");
        }
        return brands.stream()
                .map(brandEntityMapper::brand).map(brandMapper::brandDto)
                .collect(Collectors.toList());
    }
    // Método para agregar una nueva marca
    @Override
    public void addBrand(BrandDTO brandDto) {
        BrandEntity brandEntity = brandEntityMapper.brandEntity(brandMapper.brand(brandDto));
        brandRepository.save(brandEntity);
    }
    // Método para eliminar una marca por ID
    @Override
    public void deleteBrandById(Integer id) {
            brandRepository.deleteById(id);
    }
    // Método para obtener una marca por ID
    @Override
    public BrandDTO getBrandById(Integer id) {

        return brandRepository.findById(id).map(brandEntityMapper::brand).map(brandMapper::brandDto)
                        .orElseThrow(()-> new NoSuchElementException("El brand con el ID " + id + " no existe."));
    }
    // Método para actualizar una marca por ID
    @Override
    public void updateBrand(Integer id, BrandDTO brandDto) {
        Brand brand = brandMapper.brand(brandDto);
        BrandEntity brandEntity = brandEntityMapper.brandEntity(brand);
        brandEntity.setId(id);
        brandRepository.save(brandEntity);

    }
}
