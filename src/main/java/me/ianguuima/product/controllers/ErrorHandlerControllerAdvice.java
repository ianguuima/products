package me.ianguuima.product.controllers;


import me.ianguuima.product.models.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var validationError = new ValidationErrorResponse(HttpStatus.BAD_REQUEST, ex.getFieldError().getDefaultMessage());
        return ResponseEntity.status(validationError.getHttpStatus()).body(validationError);
    }

}
