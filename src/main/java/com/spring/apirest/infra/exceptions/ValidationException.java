package com.spring.apirest.infra.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(){super("Os dados de entrada não passam na validação");}
    public ValidationException(String message){super(message);}
}
