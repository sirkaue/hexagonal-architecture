package com.sirkaue.hexagonalarchitecture.infra.adapters.out;

import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPort;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.UserEntityMapper;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserPasswordAdapter implements UpdateUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User update(User user) {
        UserEntity userEntity = userJpaRepository.getReferenceById(user.getId());
        userEntity.setPassword(user.getPassword().value());
        userJpaRepository.save(userEntity);
        return userEntityMapper.toUser(userEntity);
    }
}
