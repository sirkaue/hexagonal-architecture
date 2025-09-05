package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class IncorrectCurrentPasswordException extends DomainException {

    public IncorrectCurrentPasswordException() {
        super(DomainErrorCode.INCORRECT_CURRENT_PASSWORD);
    }
}
