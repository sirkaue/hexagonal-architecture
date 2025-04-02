package com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.mapper;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.entity.UserEntity;

public interface UserEntityMapper {

    UserEntity toUserEntity(User user);

    User toUser(UserEntity userEntity);
}
