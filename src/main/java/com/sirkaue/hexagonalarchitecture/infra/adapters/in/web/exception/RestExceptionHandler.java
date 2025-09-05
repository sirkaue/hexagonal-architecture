package com.sirkaue.hexagonalarchitecture.infra.adapters.in.web.exception;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.ErrorResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.FieldErrorResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestControllerAdvice
public final class RestExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException e, HttpServletRequest request) {
        HttpStatus status = mapToHttpStatus(e.getErrorCode());
        ErrorResponse errorResponse = new ErrorResponse(request, status, e.getMessage());
        return ResponseEntity.status(status).contentType(APPLICATION_JSON).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<FieldErrorResponse> fieldErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new FieldErrorResponse(err.getField(), err.getDefaultMessage()))
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(request, HttpStatus.UNPROCESSABLE_ENTITY, fieldErrors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(APPLICATION_JSON).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e, HttpServletRequest request) {
        e.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error has occurred. Please try again later.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(APPLICATION_JSON).body(errorResponse);
    }

    private HttpStatus mapToHttpStatus(DomainErrorCode code) {
        return switch (code) {
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case EMAIL_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case INVALID_EMAIL, INVALID_PASSWORD, INVALID_NAME,
                 NAME_BLANK, NAME_TOO_LONG, PASSWORD_CONFIRMATION -> HttpStatus.BAD_REQUEST;
            case INCORRECT_CURRENT_PASSWORD, SAME_EMAIL, SAME_PASSWORD -> HttpStatus.UNPROCESSABLE_ENTITY;
        };
    }
}
