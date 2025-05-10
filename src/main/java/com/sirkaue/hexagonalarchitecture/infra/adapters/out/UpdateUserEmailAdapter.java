package com.sirkaue.hexagonalarchitecture.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPort;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.UserEntityMapper;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateUserEmailAdapter implements UpdateUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    @Transactional
    public User update(User user) {
        UserEntity userEntity = userJpaRepository.getReferenceById(user.getId());
        userEntity.setEmail(user.getEmail().value());
        userJpaRepository.save(userEntity);
        return userEntityMapper.toUser(userEntity);
    }
}
