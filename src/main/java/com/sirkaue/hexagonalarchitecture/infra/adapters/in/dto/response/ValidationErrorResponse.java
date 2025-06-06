package com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String path,
        String method,
        List<FieldErrorResponse> errors
) {

    public ValidationErrorResponse(HttpServletRequest request, HttpStatus status, List<FieldErrorResponse> errors) {
        this(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                request.getRequestURI(),
                request.getMethod(),
                errors);
    }
}
