package com.spring.apirest.services;

import com.spring.apirest.controllers.ProductController;
import com.spring.apirest.dtos.product.ProductDTO;
import com.spring.apirest.dtos.product.ProductResponseDTO;
import com.spring.apirest.infra.exceptions.DatabaseAccessException;
import com.spring.apirest.infra.exceptions.ProductAlreadyExistsException;
import com.spring.apirest.infra.exceptions.ProductNotFoundException;
import com.spring.apirest.models.products.Product;
import com.spring.apirest.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ProductResponseDTO saveProduct(@Valid ProductDTO productDto) {
        // Verifica se já existe um produto com o mesmo nome no banco de dados
        if (productRepository.findByName(productDto.name()).isPresent()) {
            String errorMessage = "Product with name " + productDto.name() + " already exists";
            throw new ProductAlreadyExistsException(errorMessage);
        }

        // Cria um novo objeto Product e copia as propriedades do DTO
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);

        // Salva o objeto no banco de dados
        productRepository.save(product);

        // Retorna o DTO do produto salvo
        return new ProductResponseDTO(product);
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> listProducts = productRepository.findAll();

        // Mapeia cada produto para seu respectivo DTO de resposta e adiciona o link para os detalhes de cada produto
        return listProducts.stream()
                .map(product -> {
                    ProductResponseDTO productDTO = new ProductResponseDTO(product);
                    addLinkToProductDetails(productDTO, product.getIdProduct());
                    return productDTO;
                })
                .collect(Collectors.toList());

    }

    /// Método para adicionar um link para os detalhes de um produto no DTO de resposta
    private void addLinkToProductDetails(ProductResponseDTO dto, UUID id) {
        Link link = linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel();
        dto.setDetailsLink(link);
    }

    public ProductResponseDTO findProduct(@Valid UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);

        // Se o produto não foi encontrado
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException();
        }

        // Adicionar um link para obter todos os produtos
        Link allProductsLink = linkTo(methodOn(ProductController.class).getAll()).withRel("Product All");

        // Criar o DTO de resposta do produto com o link para os detalhes
        ProductResponseDTO productDTO = new ProductResponseDTO(productOptional.get().getName(), productOptional.get().getValue(), allProductsLink);

        return productDTO;
    }

    public ProductResponseDTO update(@Valid UUID id, ProductDTO dto)  {
        Optional<Product> productBD = productRepository.findById(id);
        // Se o produto não foi encontrado
        if (productBD.isEmpty()) {
            throw new ProductNotFoundException();
        }

        // Atribui os dados do DTO ao modelo de produto recuperado do banco de dados
        Product productModel = productBD.get();
        BeanUtils.copyProperties(dto, productModel);

        // Salva o produto atualizado no banco de dados
        productRepository.save(productModel);

        // Retorna o DTO com os dados atualizados do produto
        return new ProductResponseDTO(productModel);
    }

    public void delete(@Valid UUID id){
        try {
            Optional<Product> productBD = productRepository.findById(id);
            // Se o produto não foi encontrado
            if (productBD.isEmpty()) {
                throw new ProductNotFoundException("Product not found with ID: " + id);
            }
            // Se o produto foi encontrado, exclui do banco de dados
            productRepository.delete(productBD.get());
        } catch (ProductNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseAccessException("Error deleting product with ID: ");
        }
    }
}


