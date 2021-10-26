package com.rutkoski.products.utils;

import com.rutkoski.products.entities.ResponseError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> messages = new ArrayList<>();
        for(ObjectError error : ex.getAllErrors()){
            messages.add(error.getDefaultMessage());
        }
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        ResponseError error = new ResponseError(
                responseStatus.value(),
                responseStatus.name(),
                ((ServletWebRequest)request).getRequest().getServletPath(),
                messages,
                Instant.now()
        );
        return ResponseEntity.status(responseStatus).body(error);
    }
}
