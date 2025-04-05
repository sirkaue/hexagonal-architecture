package com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.mapper.impl;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.mapper.UserEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public UserEntity toUserEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    @Override
    public User toUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getPassword());
    }
}
