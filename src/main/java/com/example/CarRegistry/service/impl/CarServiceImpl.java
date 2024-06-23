package com.example.CarRegistry.service.impl;

import com.example.CarRegistry.controller.dto.BrandDTO;
import com.example.CarRegistry.controller.dto.CarBrandDTO;
import com.example.CarRegistry.controller.dto.CarDTO;
import com.example.CarRegistry.controller.mapper.BrandMapper;
import com.example.CarRegistry.controller.mapper.CarMapper;
import com.example.CarRegistry.repository.BrandRepository;
import com.example.CarRegistry.repository.CarRepository;
import com.example.CarRegistry.repository.entity.CarEntity;
import com.example.CarRegistry.repository.mapper.BrandEntityMapper;
import com.example.CarRegistry.repository.mapper.CarEntityMapper;
import com.example.CarRegistry.service.BrandService;
import com.example.CarRegistry.service.CarService;
import com.example.CarRegistry.service.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarEntityMapper carEntityMapper;
    @Autowired
    private BrandEntityMapper brandEntityMapper;

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;

    // Método para obtener todos los coches
    @Override
    public List<CarDTO> getAllCars() {
        try{
        return carRepository.findAll().stream()
                .map(carEntityMapper::car).map(carMapper::carDto)
                .collect(Collectors.toList());
        }catch (DataAccessException e){
            log.error("Error al obtener todos los coches de la BBDD "+e.getMessage(), e);
            throw new RuntimeException("Error al obtener los coches de la BBDD", e);
        }
    }
    // Método para obtener todos los coches con sus marcas
    @Override
    public  List<CarBrandDTO> getAllCarBrand(){
        try{
            return  carRepository.findAll().stream().map(carEntity -> {
                // Obtiene la marca asociada al coche
                BrandDTO brandDTO = brandRepository.findById(carEntity.getBrandid())
                        .map(brandEntity -> brandMapper.brandDto(brandEntityMapper.brand(brandEntity)))
                        .orElseThrow(()-> new RuntimeException("Brand no encontrado"));
                // Convierte el coche y la marca a CarBrandDTO
                CarDTO carDTO = carMapper.carDto(carEntityMapper.car(carEntity));
                return CarBrandDTO.builder()
                        .id(carDTO.getId())
                        .brand(brandDTO)
                        .model(carDTO.getModel())
                        .year(carDTO.getYear())
                        .numberofdoors(carDTO.getNumberofdoors())
                        .isconvertible(carDTO.getIsconvertible())
                        .mileage(carDTO.getMileage())
                        .price(carDTO.getPrice())
                        .description(carDTO.getDescription())
                        .colour(carDTO.getColour())
                        .fueltype(carDTO.getFueltype())
                        .build();
            }).collect(Collectors.toList());
        }catch (DataAccessException e){
            log.error("Error al obtener todos los coches con la marca de la BBDD " + e.getMessage(), e);
            throw new RuntimeException("Error al obtener los coches con la marca de la BBDD", e);
        }
    }
    // Método para agregar un nuevo coche
    @Override
    public void addCar(CarDTO carDto) {
        if(!brandRepository.existsById(carDto.getBrandid())){
            log.error("El brandId {} especificado no existe", carDto.getBrandid());
            throw new IllegalArgumentException("El BrandId especificado no existe");
        }else{
            CarEntity carEntity = carEntityMapper.carEntity(carMapper.car(carDto));
            carRepository.save(carEntity);
        }
    }
    // Método para eliminar un coche por ID
    @Override
    public void deleteCarById(Integer id) {
        carRepository.deleteById(id);
    }
    // Método para obtener un coche por ID
    @Override
    public CarDTO getCarById(Integer id) {
        return carRepository.findById(id).map(carEntityMapper::car).map(carMapper::carDto)
                .orElseThrow(()-> new NoSuchElementException("El coche con el ID " + id + " no existe."));
    }
    // Método para obtener un coche y su marca por ID
    @Override
    public CarBrandDTO getCarBrandById(Integer id){
        try {
            CarEntity carEntity = carRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("El coche con el ID " + id + " no existe."));

            // Obtiene la marca asociada al coche
            BrandDTO brandDTO = brandRepository.findById(carEntity.getBrandid())
                    .map(brandEntity -> brandMapper.brandDto(brandEntityMapper.brand(brandEntity)))
                    .orElseThrow(()-> new RuntimeException("Brand no encontrado"));
            // Convierte el coche y la marca a CarBrandDTO
            CarDTO carDTO = carMapper.carDto(carEntityMapper.car(carEntity));

            return CarBrandDTO.builder()
                    .id(carDTO.getId())
                    .brand(brandDTO)
                    .model(carDTO.getModel())
                    .year(carDTO.getYear())
                    .numberofdoors(carDTO.getNumberofdoors())
                    .isconvertible(carDTO.getIsconvertible())
                    .mileage(carDTO.getMileage())
                    .price(carDTO.getPrice())
                    .description(carDTO.getDescription())
                    .colour(carDTO.getColour())
                    .fueltype(carDTO.getFueltype())
                    .build();

        }catch (DataAccessException e){
            log.error("Error al obtener el coche con la marca de la BBDD " + e.getMessage(), e);
            throw new RuntimeException("Error al obtener el coche con la marca de la BBDD", e);
        }

    }
    // Método para actualizar un coche por ID
    @Override
    public void updateCar(Integer id, CarDTO carDto) {
        Car car = carMapper.car(carDto);
        CarEntity carEntity = carEntityMapper.carEntity(car);
        carEntity.setId(id);
        carRepository.save(carEntity);
    }
}
