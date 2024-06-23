package com.example.CarRegistry.controller;

import com.example.CarRegistry.controller.dto.CarBrandDTO;
import com.example.CarRegistry.controller.dto.CarDTO;
import com.example.CarRegistry.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
@Slf4j
@RestController
public class CarController {
    @Value("${car.defaultMake}")
    private String defaultMake;

    @Autowired
    private CarService carService;
    // Método para obtener todos los coches
    @GetMapping("/cars")
    public List<CarDTO> showCars(){
        log.info("Bienvenido al concesionario de: {}",defaultMake);
        System.out.println("Lista de coches");
        carService.getAllCars().forEach(car -> System.out.println(car));
        return carService.getAllCars();
    }
    // Método para agregar un nuevo coche
    @PostMapping("/addCar")
    public ResponseEntity<?> addCar(@RequestBody CarDTO carDto){
        try {
            carService.addCar(carDto);
            log.info("Coche guardado");
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e){
            log.error("Error al agregar el coche "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch(Exception e){
            log.error("Error al guardar el coche", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Método para eliminar un coche por ID
    @DeleteMapping("/delCar/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Integer id){
        try{
            carService.deleteCarById(id);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método para actualizar información de un coche por ID
    @PutMapping("/putCar/{id}")
    public ResponseEntity<?> putCar(@PathVariable Integer id, @RequestBody CarDTO carDto){
        try{
            carService.updateCar(id, carDto);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception e){
            log.error("Error al actualizar el coche con id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Método para obtener un coche por ID
    @GetMapping("/getCar/{id}")
    public ResponseEntity<?>  getCarId(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(carService.getCarById(id));
        }catch (NoSuchElementException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error("Error, Con el id: "+id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Método para obtener todos los coches con sus marcas
    @GetMapping("/carsBrand")
    public ResponseEntity<List<CarBrandDTO>> getAllCarsBrand(){
        try{
            List<CarBrandDTO> carsBrand = carService.getAllCarBrand();
            return ResponseEntity.ok(carsBrand);
        }catch (Exception e){
            log.error("Error al obtener todos los coches con sus marcas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Método para obtener un coche y su marca por ID
    @GetMapping("/carBrand/{id}")
    public ResponseEntity<?> getCarBrand(@PathVariable Integer id){
        try {
            CarBrandDTO carBrandDTO = carService.getCarBrandById(id);
            return ResponseEntity.ok(carBrandDTO);
        }catch (NoSuchElementException e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error("Error al obtener el coche y marca: "+id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
