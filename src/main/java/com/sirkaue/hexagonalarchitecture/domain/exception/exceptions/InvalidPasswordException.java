package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String message) {
        super(message);
    }
}

