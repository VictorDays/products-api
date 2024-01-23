package com.spring.apirest.controllers;

import com.spring.apirest.dtos.ProductRecordDTO;
import com.spring.apirest.models.Product;
import com.spring.apirest.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping(value = "/products")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDto) {
        var product = new Product();
        BeanUtils.copyProperties(productRecordDto, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable(value="id") UUID id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }
}
