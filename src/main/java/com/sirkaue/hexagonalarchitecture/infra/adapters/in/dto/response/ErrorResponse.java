package com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(

        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        String method,
        List<FieldErrorResponse> errors) {

    // construtor para mensagem simples
    public ErrorResponse(HttpServletRequest request, HttpStatus status, String message) {
        this(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),  // preenche o campo 'error'
                message,
                request.getRequestURI(),   // pega o path da requisição
                request.getMethod(),       // pega o método HTTP
                null                       // sem lista de erros
        );
    }

    // construtor para lista de erros
    public ErrorResponse(HttpServletRequest request, HttpStatus status, List<FieldErrorResponse> errors) {
        this(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                null,                      // sem mensagem simples
                request.getRequestURI(),
                request.getMethod(),
                errors                     // lista de erros
        );
    }
}
