package com.example.service.search.config.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exceptions {
    @ExceptionHandler({SearchExceptions.class})
    public ResponseEntity erroUsers(){
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity argument(MethodArgumentNotValidException ex){
        var erros =  ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(MensagemErros::new).toList());

    }
    public record MensagemErros(String field, String description){
        public MensagemErros(FieldError error){
            this(error.getField(),error.getDefaultMessage());
        }

    }
}
