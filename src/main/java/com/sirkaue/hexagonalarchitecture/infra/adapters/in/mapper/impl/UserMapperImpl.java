package com.sirkaue.hexagonalarchitecture.infra.adapters.in.mapper.impl;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Name;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.request.UserRequest;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.UserResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public final class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequest userRequest) {
        return new User(
                null,
                new Name(userRequest.name()),
                new Email(userRequest.email()),
                new Password(userRequest.password()));
    }

    @Override
    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getName().value(), user.getEmail().value());
    }
}
