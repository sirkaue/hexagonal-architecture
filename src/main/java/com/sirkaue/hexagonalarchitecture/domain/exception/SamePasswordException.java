package com.sirkaue.hexagonalarchitecture.domain.exception;

public class SamePasswordException extends RuntimeException {

    public SamePasswordException(String message) {
        super(message);
    }
}
