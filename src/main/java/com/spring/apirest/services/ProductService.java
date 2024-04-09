package com.spring.apirest.services;

import com.spring.apirest.controllers.ProductController;
import com.spring.apirest.dtos.product.ProductDTO;
import com.spring.apirest.dtos.product.ProductResponseDTO;
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
        var product = new Product();

        // Copia as propriedades do ProductDTO para o objeto Product
        BeanUtils.copyProperties(productDto, product);

        // Salva objeto no banco de dados
        productRepository.save(product);

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

        // Verificar se o produto não foi encontrado
        if (productOptional.isEmpty()) {
            // Se o produto não for encontrado, retorna uma resposta com status 404
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no product saved with this ID");
            return null;
        }

        // Adicionar um link para obter todos os produtos
        Link allProductsLink = linkTo(methodOn(ProductController.class).getAll()).withRel("Product All");

        // Criar o DTO de resposta do produto com o link para os detalhes
        ProductResponseDTO productDTO = new ProductResponseDTO(productOptional.get().getName(), productOptional.get().getValue(), allProductsLink);

        return productDTO;
    }

    public ProductResponseDTO update(@Valid UUID id, ProductDTO dto)  {
        Optional<Product> productBD = productRepository.findById(id);
        if (productBD.isEmpty()) {
            return null;
        }

        // Atribui os dados do DTO ao modelo de produto recuperado do banco de dados
        Product productModel = productBD.get();
        BeanUtils.copyProperties(dto, productModel);

        // Salva o produto atualizado no banco de dados
        productRepository.save(productModel);

        // Retorna o DTO com os dados atualizados do produto
        return new ProductResponseDTO(productModel);
    }

    public String delete(@Valid UUID id){
        Optional<Product> productBD = productRepository.findById(id);
        if (productBD.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productRepository.delete(productBD.get());

        return "The product has been deleted";
    }
}


