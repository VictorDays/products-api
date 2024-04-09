package com.spring.apirest.infra.exceptions;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(){super("Product already exists in the database");}
    public ProductAlreadyExistsException(String message){super(message);}
}
