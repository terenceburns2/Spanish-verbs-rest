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

    @ExceptionHandler(InvalidAccessTokenException.class)
    public ResponseEntity<ErrorDetails> exceptionInvalidAccessTokenHandler(InvalidAccessTokenException e) {
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

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
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ErrorDetails> exceptionAccountExitsHandler() {
        ErrorDetails errorDetails = new ErrorDetails("This email already exists. Try signing in.");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailDoesNotExistException.class)
    public ResponseEntity<ErrorDetails> exceptionAccountDoesNotExistHandler() {
        ErrorDetails errorDetails = new ErrorDetails("Email does not exist.");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
