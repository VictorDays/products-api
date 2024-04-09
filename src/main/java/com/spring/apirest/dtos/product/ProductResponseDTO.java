package com.spring.apirest.dtos.product;

import com.spring.apirest.models.products.Product;
import org.springframework.hateoas.Link;

import java.math.BigDecimal;

public class ProductResponseDTO {
    private String name;
    private BigDecimal value;
    private Link detailsLink;

    // Construtor para converter objeto em ResponseDTO
    public ProductResponseDTO(Product product) {
        this.name = product.getName();
        this.value = product.getValue();
        this.detailsLink = product.getDetailsLink();
    }

    // Construtor para inserir o link da lista de todos produtos
    public ProductResponseDTO(String name, BigDecimal value, Link allProductsLink) {
        this.name = name;
        this.value = value;
        this.detailsLink = allProductsLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Link getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(Link detailsLink) {
        this.detailsLink = detailsLink;
    }
}

