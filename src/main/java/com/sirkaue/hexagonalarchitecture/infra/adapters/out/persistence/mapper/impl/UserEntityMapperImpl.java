package com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.impl;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper.UserEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public UserEntity toUserEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail().value(), user.getPassword().value());
    }

    @Override
    public User toUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(), new Email(userEntity.getEmail()), new Password(userEntity.getPassword()));
    }
}
