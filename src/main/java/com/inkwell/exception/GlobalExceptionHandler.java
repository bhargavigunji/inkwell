package com.inkwell.exception;


import com.inkwell.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex){
        ErrorResponse error = new ErrorResponse(
                400, ex.getMessage(), LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
                404, ex.getMessage(),LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> accessNotFound(ForbiddenException ex){
        ErrorResponse error = new ErrorResponse(
                403, ex.getMessage(), LocalDateTime.now()
        );
        return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> emailAlreadyFound(DataIntegrityViolationException ex){
        ErrorResponse error= new ErrorResponse(
                409, "a record with this value already exists or is in use", LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
