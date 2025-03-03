package com.demo.library.exception;

import com.demo.library.constants.ConstantMessage;
import com.demo.library.dto.ResponseDTO;
import com.demo.library.service.BookDetailsService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger gehLogger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseDTO handleValidationException(ValidationException ex, HttpServletRequest request) {
        return new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Enter correct data", Collections.emptyList(), request.getRequestURI());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseDTO handleNullPointerException(NullPointerException ex, HttpServletRequest request) {
        return new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Null value not allowed", Collections.emptyList(), request.getRequestURI());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseDTO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Incorrect method type", Collections.emptyList(), request.getRequestURI());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseDTO handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        return new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "API not found", Collections.emptyList(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO handleException(Exception ex, HttpServletRequest request) {
        gehLogger.error("Operation failed method- {} exception- {}", request.getRequestURI(), ex.getLocalizedMessage());
        return new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Error occurs", Collections.emptyList(), request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseDTO handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        return new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
    }

}
