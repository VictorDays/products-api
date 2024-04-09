package com.spring.apirest.infra.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(){super("Product is not found in the database");}
    public ProductNotFoundException(String message){super(message);}
}
