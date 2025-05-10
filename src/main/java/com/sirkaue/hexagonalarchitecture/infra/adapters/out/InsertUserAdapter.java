package com.sirkaue.hexagonalarchitecture.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.application.ports.out.InsertUserPort;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.UserEntityMapper;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsertUserAdapter implements InsertUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void insert(User user) {
        UserEntity entity = userEntityMapper.toUserEntity(user);
        userJpaRepository.save(entity);
    }
}
