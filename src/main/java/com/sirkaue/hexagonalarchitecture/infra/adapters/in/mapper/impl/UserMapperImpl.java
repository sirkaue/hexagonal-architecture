package com.sirkaue.hexagonalarchitecture.infra.adapters.in.mapper.impl;

import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.request.UserRequest;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.UserResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.mapper.UserMapper;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequest userRequest) {
        return new User(null, userRequest.name(), new Email(userRequest.email()), new Password(userRequest.password()));
    }

    @Override
    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail().value());
    }
}
