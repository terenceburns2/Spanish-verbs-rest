package com.terenceapps.spanishverbs.exception;

import com.terenceapps.spanishverbs.model.ErrorDetails;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> exceptionRequestParamNotValidHandler(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();

        e.getConstraintViolations().stream().forEach(err -> errors.add(err.getMessage()));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> exceptionArgumentNotValidHandler(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();

        e.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDetails> exceptionDataAccessHandler() {
        ErrorDetails errorDetails = new ErrorDetails("Failed to process request.");
        return ResponseEntity.internalServerError().body(errorDetails);
    }

    @ExceptionHandler(EmailDoesNotExistException.class)
    public ResponseEntity<ErrorDetails> exceptionAccountDoesNotExistHandler() {
        ErrorDetails errorDetails = new ErrorDetails("Email does not exist.");
        return ResponseEntity.badRequest().body(errorDetails);
    }
}
