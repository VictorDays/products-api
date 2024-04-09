package com.spring.apirest.infra.error;

import com.spring.apirest.infra.exceptions.DatabaseAccessException;
import com.spring.apirest.infra.exceptions.ProductAlreadyExistsException;
import com.spring.apirest.infra.exceptions.ProductNotFoundException;
import com.spring.apirest.infra.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/* Essa anotação é do Spring Framework e é utilizada para lidar com exceções lançadas em
qualquer lugar da sua aplicação, não só pelo controller.
*/
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<RestErrorMessage> productNotFoundException(ProductNotFoundException exception){
        RestErrorMessage response = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(ProductAlreadyExistsException.class)
    private ResponseEntity<RestErrorMessage> productAlreadyExistsException(ProductAlreadyExistsException exception){
        RestErrorMessage response = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<RestErrorMessage> validationException(ValidationException exception){
        RestErrorMessage response = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(DatabaseAccessException.class)
    private ResponseEntity<RestErrorMessage> databaseAccessException(DatabaseAccessException exception){
        RestErrorMessage response = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // Método para lidar com exceções não especificadas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleException(Exception ex) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
