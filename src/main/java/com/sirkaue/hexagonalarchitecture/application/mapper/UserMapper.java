package com.sirkaue.hexagonalarchitecture.application.mapper;

import com.sirkaue.hexagonalarchitecture.application.dto.request.UserRequest;
import com.sirkaue.hexagonalarchitecture.application.dto.response.UserResponse;
import com.sirkaue.hexagonalarchitecture.domain.model.User;

public interface UserMapper {

    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);
}
