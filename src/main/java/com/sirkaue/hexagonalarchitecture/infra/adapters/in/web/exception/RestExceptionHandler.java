package com.sirkaue.hexagonalarchitecture.infra.adapters.in.web.exception;

import com.sirkaue.hexagonalarchitecture.domain.exception.*;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.ErrorResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.FieldErrorResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return createErrorResponse(request, HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return createErrorResponse(request, HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        return createErrorResponse(request, HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(IncorrectCurrentPasswordException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectCurrentPasswordException(IncorrectCurrentPasswordException ex, HttpServletRequest request) {
        return createErrorResponse(request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<ErrorResponse> handleSamePasswordException(SamePasswordException ex, HttpServletRequest request) {
        return createErrorResponse(request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    public ResponseEntity<ErrorResponse> handlePasswordConfirmationException(PasswordConfirmationException ex, HttpServletRequest request) {
        return createErrorResponse(request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(request, HttpStatus.UNPROCESSABLE_ENTITY, errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(
            HttpServletRequest request,
            HttpStatus status,
            Exception exception
    ) {
        ErrorResponse error = new ErrorResponse(request, status, exception);
        return ResponseEntity.status(status).contentType(APPLICATION_JSON).body(error);
    }
}
