package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.FindUserByIdUseCase;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    private static final Logger log = LoggerFactory.getLogger(FindUserByIdUseCaseImpl.class);

    private final EntityFinderHelper entityFinderHelper;

    public FindUserByIdUseCaseImpl(EntityFinderHelper entityFinderHelper) {
        this.entityFinderHelper = entityFinderHelper;
    }

    @Override
    public User execute(Long id) {
        log.info("Finding user with id: {}", id);
        return entityFinderHelper.findUserByIdOrThrow(id);
    }
}
