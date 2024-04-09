package com.spring.apirest.controllers;

import com.spring.apirest.dtos.product.ProductDTO;
import com.spring.apirest.dtos.product.ProductResponseDTO;
import com.spring.apirest.models.products.Product;
import com.spring.apirest.repositories.ProductRepository;
import com.spring.apirest.services.ProductService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/products")
    public ResponseEntity<ProductResponseDTO> save(@RequestBody @Valid ProductDTO productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productDto));
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findProduct(id));
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<ProductResponseDTO> update(@RequestBody @Valid ProductDTO productDto,@PathVariable(value = "id") UUID id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productService.update(id,productDto));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") UUID id) {
        try {
            productService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

}
