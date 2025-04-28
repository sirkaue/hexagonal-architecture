package com.sirkaue.hexagonalarchitecture.domain.exception;

public class PasswordConfirmationException extends RuntimeException {

    public PasswordConfirmationException(String message) {
        super(message);
    }
}
