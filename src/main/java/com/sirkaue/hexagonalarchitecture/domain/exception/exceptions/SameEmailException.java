package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class SameEmailException extends DomainException {

    public SameEmailException() {
        super(DomainErrorCode.SAME_EMAIL);
    }
}
