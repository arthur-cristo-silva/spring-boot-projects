package com.arthur.adi.controller;

import com.arthur.adi.exception.AdiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AdiException.class)
    public ProblemDetail handleAdiException(AdiException e) {
        return e.toProblemDetail();
    }

    /*
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setDetail(e.getLocalizedMessage());
        return pb;
    }

     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        var fieldErrors = e.getFieldErrors()
                .stream()
                .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
                .toList();

        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Required parameters aren't valid.");
        pb.setProperty("invalid-params", fieldErrors);
        return pb;
    }

    private record InvalidParam(String field, String defaultMessage) {}

    /*
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setDetail(e.getLocalizedMessage());
        return pb;
    }

     */
}
