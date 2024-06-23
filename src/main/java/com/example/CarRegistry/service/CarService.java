package com.example.CarRegistry.service;

import com.example.CarRegistry.controller.dto.CarBrandDTO;
import com.example.CarRegistry.controller.dto.CarDTO;

import java.util.List;

public interface CarService {
    List<CarDTO> getAllCars();
    List<CarBrandDTO> getAllCarBrand();
    void addCar (CarDTO carDto);

    void deleteCarById(Integer id);

    CarDTO getCarById(Integer id);
    CarBrandDTO getCarBrandById(Integer id);

    void updateCar(Integer id, CarDTO carDto);
}
