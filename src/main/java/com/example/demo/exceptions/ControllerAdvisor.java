package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<?> defaultExceptionHandler(RuntimeException ex, WebRequest request){
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(RegisteredClientNotFoundException.class)
//    ResponseEntity<?> clientNotFoundEx(RegisteredClientNotFoundException ex, WebRequest request){
//        Map<String, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(RedirectUriDoesnotMatchException.class)
//    ResponseEntity<?> uriDoesnotMatch(RedirectUriDoesnotMatchException ex, WebRequest request){
//        Map<String, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(WrongResponseTypeException.class)
//    ResponseEntity<?> wrongResponseType(WrongResponseTypeException ex, WebRequest request){
//        Map<String, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NotAuthorizedClientException.class)
//    ResponseEntity<?> wrongResponseType(NotAuthorizedClientException ex, WebRequest request){
//        Map<String, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NotValidTokenException.class)
//    ResponseEntity<?> wrongResponseType(NotValidTokenException ex, WebRequest request){
//        Map<String, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(WrongGrantTypeException.class)
//    ResponseEntity<?> wrongResponseType(WrongGrantTypeException ex, WebRequest request){
//        Map<String, Object> body = new HashMap<>();
//        body.put("message", ex.getMessage());
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }
}
