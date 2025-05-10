package com.sirkaue.hexagonalarchitecture.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.application.ports.out.FindUserByIdPort;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.UserEntityMapper;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindUserByIdAdapter implements FindUserByIdPort {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        Optional<UserEntity> userEntity = userJpaRepository.findById(id);
        return userEntity.map(userEntityMapper::toUser);
    }
}
