package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

public class IncorrectCurrentPasswordException extends RuntimeException {

    public IncorrectCurrentPasswordException(String message) {
        super(message);
    }
}
