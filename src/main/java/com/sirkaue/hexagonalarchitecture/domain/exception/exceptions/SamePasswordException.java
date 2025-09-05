package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

public class SamePasswordException extends RuntimeException {

    public SamePasswordException(String message) {
        super(message);
    }
}
