package com.example.xml.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InvalidXMLException.class)
    public ResponseEntity<?> handleStorageException(InvalidXMLException exc) {
        return ResponseEntity.badRequest().body("XML validation error: " + exc.getMessage());
    }
}
