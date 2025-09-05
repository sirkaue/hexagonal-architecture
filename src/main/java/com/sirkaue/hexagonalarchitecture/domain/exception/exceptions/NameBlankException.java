package com.sirkaue.hexagonalarchitecture.domain.exception.exceptions;

import com.sirkaue.hexagonalarchitecture.domain.exception.DomainErrorCode;
import com.sirkaue.hexagonalarchitecture.domain.exception.DomainException;

public final class NameBlankException extends DomainException {

    public NameBlankException() {
        super(DomainErrorCode.NAME_BLANK);
    }
}
