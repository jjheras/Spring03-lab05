package com.example.CarRegistry.controller;

import com.example.CarRegistry.controller.dto.BrandDTO;
import com.example.CarRegistry.controller.dto.CarDTO;
import com.example.CarRegistry.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
@Slf4j
@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    // Método para obtener todas las marcas
    @GetMapping("/brand")
    public List<BrandDTO> showBrand(){
        //log.info("Bienvenido al concesionario de: {}",defaultMake);
        System.out.println("Lista de brand");
        brandService.getAllBrands().forEach(brand-> System.out.println(brand));
        return brandService.getAllBrands();
    }
    // Método para agregar una nueva marca
    @PostMapping("/addBrand")
    public ResponseEntity<?> addBrand(@RequestBody BrandDTO brandDTO){
        try{
            brandService.addBrand(brandDTO);
            log.info("Brand guardado");
            return ResponseEntity.ok().build();
        }catch(Exception e){
            log.error("Error al guardar el Brand", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Método para eliminar una marca por ID
    @DeleteMapping("/delBrand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Integer id){
        try{
            brandService.deleteBrandById(id);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    // Método para actualizar información de una marca por ID
    @PutMapping("/putBrand/{id}")
    public ResponseEntity<?> brandCar(@PathVariable Integer id, @RequestBody BrandDTO brandDTO){
        try{
            brandService.updateBrand(id,brandDTO);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception e){
            log.error("Error al actualizar el Brand con id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Método para obtener una marca por ID
    @GetMapping("/getBrand/{id}")
    public ResponseEntity<?>  getBrandId(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(brandService.getBrandById(id));
        }catch (NoSuchElementException e){
            log.error("El Brand con el ID " + id + " no existe.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error("Error, Con el id: "+id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
