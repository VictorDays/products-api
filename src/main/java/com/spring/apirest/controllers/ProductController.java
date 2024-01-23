package com.spring.apirest.controllers;

import com.spring.apirest.dtos.ProductRecordDTO;
import com.spring.apirest.models.Product;
import com.spring.apirest.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
