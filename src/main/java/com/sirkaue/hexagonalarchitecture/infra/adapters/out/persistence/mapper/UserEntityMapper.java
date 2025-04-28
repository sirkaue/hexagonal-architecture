package com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.mapper;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;

public interface UserEntityMapper {

    UserEntity toUserEntity(User user);

    User toUser(UserEntity userEntity);
}
