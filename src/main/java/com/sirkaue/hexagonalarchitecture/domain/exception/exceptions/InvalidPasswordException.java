package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class InvalidPasswordException extends DomainException {

    public InvalidPasswordException() {
        super(DomainErrorCode.INVALID_PASSWORD);
    }
}

