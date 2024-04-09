package com.spring.apirest.infra.exceptions;

public class DatabaseAccessException extends RuntimeException{
    public DatabaseAccessException(){super("A user trying to access an operation that he does not have permission to perform");}
    public DatabaseAccessException(String message){super(message);}
}
