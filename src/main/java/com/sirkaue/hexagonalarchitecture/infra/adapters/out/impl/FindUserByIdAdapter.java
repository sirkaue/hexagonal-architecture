package com.sirkaue.hexagonalarchitecture.infra.adapters.out.impl;

import com.sirkaue.hexagonalarchitecture.application.ports.out.FindUserByIdPort;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.UserRepository;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindUserByIdAdapter implements FindUserByIdPort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userEntityMapper::toUser);
    }
}
