package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class EmailAlreadyExistsException extends DomainException {

    public EmailAlreadyExistsException() {
        super(DomainErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
