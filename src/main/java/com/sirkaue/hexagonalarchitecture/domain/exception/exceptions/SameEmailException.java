package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

public class SameEmailException extends RuntimeException {

    public SameEmailException(String message) {
        super(message);
    }
}
