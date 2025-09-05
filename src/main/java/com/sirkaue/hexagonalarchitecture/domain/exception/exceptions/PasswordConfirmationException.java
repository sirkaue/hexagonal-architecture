package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class PasswordConfirmationException extends DomainException {

    public PasswordConfirmationException() {
        super(DomainErrorCode.PASSWORD_CONFIRMATION);
    }
}
