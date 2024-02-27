package com.spring.apirest.repositories;

import com.spring.apirest.models.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
//Aqui fica consultas especificas
}
