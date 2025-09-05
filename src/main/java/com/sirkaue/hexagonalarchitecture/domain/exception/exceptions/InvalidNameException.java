package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class InvalidNameException extends DomainException {

    public InvalidNameException() {
        super(DomainErrorCode.INVALID_NAME);
    }
}
