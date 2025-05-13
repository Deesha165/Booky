package com.example.Bookify.exception;

import com.example.Bookify.dto.error.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        Map<String, String> details = new LinkedHashMap<>();

        details.put("general", e.getMessage());
        return buildErrorResponse( e.getMessage(), NOT_FOUND,details);
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException e) {
        Map<String, String> details = new LinkedHashMap<>();

        details.put("general", e.getMessage());

        return buildErrorResponse( e.getMessage(), CONFLICT, details);
    }

    @ExceptionHandler(IllegalActionException.class)
    public ResponseEntity<ErrorResponse> handleIllegalActionException(IllegalActionException e) {

        Map<String, String> details = new LinkedHashMap<>();

        details.put("general", e.getMessage());
        return buildErrorResponse( e.getMessage(), BAD_REQUEST,details);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestParameterException(MissingServletRequestParameterException e) {
        Map<String, String> details = new LinkedHashMap<>();
        details.put("name", e.getParameterName());
        details.put("type", e.getParameterType());
        details.put("general", e.getMessage());

        return buildErrorResponse( "Missing request parameter", BAD_REQUEST, details);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Map<String, String> details = new HashMap<>();
        details.put("requiredType", e.getRequiredType().getSimpleName());
        details.put("passedValue", e.getValue().toString());
        details.put("general", e.getMessage());
        return buildErrorResponse( "Method argument type mismatch", BAD_REQUEST, details);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                details.put(error.getField(), error.getDefaultMessage())
        );
        details.put("general", ex.getMessage());
        return buildErrorResponse( "Validation error", BAD_REQUEST, details);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        Map<String, String> details = new HashMap<>();
        details.put("general", e.getMessage());
        return buildErrorResponse( e.getMessage(), FORBIDDEN,details);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        Map<String, String> details = new HashMap<>();
        details.put("general", e.getMessage());
        return buildErrorResponse(e.getMessage(), UNAUTHORIZED,details);
    }


    private ResponseEntity<ErrorResponse> buildErrorResponse( String message, HttpStatus status, Map<String, String> details) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .fieldErrors(details)
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }
}
