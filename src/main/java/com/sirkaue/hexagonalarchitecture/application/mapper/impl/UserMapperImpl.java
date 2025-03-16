package com.sirkaue.hexagonalarchitecture.application.mapper.impl;

import com.sirkaue.hexagonalarchitecture.application.dto.request.UserRequest;
import com.sirkaue.hexagonalarchitecture.application.dto.response.UserResponse;
import com.sirkaue.hexagonalarchitecture.application.mapper.UserMapper;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        return new User(null, userRequest.email(), userRequest.email(), userRequest.password());
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
