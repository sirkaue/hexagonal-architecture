package com.sirkaue.hexagonalarchitecture.infra.adapters.in.web.controller;

import com.sirkaue.hexagonalarchitecture.application.dto.request.UserRequest;
import com.sirkaue.hexagonalarchitecture.application.mapper.UserMapper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.InsertUserUseCase;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final InsertUserUseCase insertUserUseCase;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        insertUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
