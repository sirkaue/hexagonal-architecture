package com.sirkaue.hexagonalarchitecture.infra.adapters.in.web.exception;

import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.ErrorResponse;
import com.sirkaue.hexagonalarchitecture.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpServletRequest request, HttpStatus status, Exception exception) {
        ErrorResponse error = new ErrorResponse(request, status, exception);
        return ResponseEntity
                .status(status)
                .contentType(APPLICATION_JSON)
                .body(error);
    }
}
