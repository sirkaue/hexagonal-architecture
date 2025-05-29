package com.sirkaue.hexagonalarchitecture.integration.infra.adapters.support.helper;

import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.UserJpaRepository;
import com.sirkaue.hexagonalarchitecture.integration.infra.adapters.support.factory.UserEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTestDataHelper {

    @Autowired
    private UserJpaRepository userJpaRepository;

    public UserEntity persistDefaultUser() {
        return userJpaRepository.save(UserEntityFactory.defaultUser());
    }
}

