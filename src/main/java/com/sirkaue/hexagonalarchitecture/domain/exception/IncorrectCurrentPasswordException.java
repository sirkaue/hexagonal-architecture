package com.sirkaue.hexagonalarchitecture.domain.exception;

public class IncorrectCurrentPasswordException extends RuntimeException {

    public IncorrectCurrentPasswordException(String message) {
        super(message);
    }
}
