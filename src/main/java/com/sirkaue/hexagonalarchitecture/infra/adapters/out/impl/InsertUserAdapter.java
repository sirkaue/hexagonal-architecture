package com.sirkaue.hexagonalarchitecture.infra.adapters.out.impl;

import com.sirkaue.hexagonalarchitecture.application.ports.out.InsertUserPort;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.UserRepository;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsertUserAdapter implements InsertUserPort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void insert(User user) {
        UserEntity entity = userEntityMapper.toUserEntity(user);
        userRepository.save(entity);
    }
}
