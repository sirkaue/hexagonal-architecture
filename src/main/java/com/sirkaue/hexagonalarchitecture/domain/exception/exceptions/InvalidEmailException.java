package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }
}
