package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class InvalidEmailException extends DomainException {

    public InvalidEmailException() {
        super(DomainErrorCode.INVALID_EMAIL);
    }
}
