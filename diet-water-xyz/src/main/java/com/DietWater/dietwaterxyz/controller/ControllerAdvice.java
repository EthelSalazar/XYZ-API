package com.DietWater.dietwaterxyz.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = {IllegalArgumentException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<VndErrors> handleOutIllegalException(Exception e, WebRequest request){
        VndErrors errors = new VndErrors(request.toString(), e.getMessage());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class, NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> handleNotFoundException(Exception e, WebRequest request){
        VndErrors errors = new VndErrors(request.toString(), e.getMessage());
        return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
    }

}
