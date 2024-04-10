package com.spring.apirest.repositories;

import com.spring.apirest.models.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    //Se o produto com o nome fornecido existir, ele será retornado encapsulado em um Optional, caso contrário, será retornado um Optional vazio.
    Optional<Product> findByName(String name);
}
