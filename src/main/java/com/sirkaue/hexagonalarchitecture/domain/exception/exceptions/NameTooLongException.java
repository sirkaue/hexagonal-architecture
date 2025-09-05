package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class NameTooLongException extends DomainException {

    public NameTooLongException() {
        super(DomainErrorCode.NAME_TOO_LONG);
    }
}
