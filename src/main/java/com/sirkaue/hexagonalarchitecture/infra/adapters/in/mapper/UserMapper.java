package com.sirkaue.hexagonalarchitecture.infra.adapters.in.mapper;


import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.request.UserRequest;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.UserResponse;

public interface UserMapper {

    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);
}
