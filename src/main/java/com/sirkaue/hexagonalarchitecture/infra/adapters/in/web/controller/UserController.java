package com.sirkaue.hexagonalarchitecture.infra.adapters.in.web.controller;

import com.sirkaue.hexagonalarchitecture.application.dto.request.UpdateEmailRequest;
import com.sirkaue.hexagonalarchitecture.application.dto.request.UserRequest;
import com.sirkaue.hexagonalarchitecture.application.dto.response.UserResponse;
import com.sirkaue.hexagonalarchitecture.application.mapper.UserMapper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.FindUserByIdUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.in.InsertUserUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.in.UpdateUserEmailUseCase;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final InsertUserUseCase insertUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UpdateUserEmailUseCase updateUserEmailUseCase;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        insertUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable final Long id) {
        User user = findUserByIdUseCase.execute(id);
        UserResponse response = userMapper.toUserResponse(user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<Void> updateEmail(@PathVariable final Long id, @RequestBody UpdateEmailRequest request) {
        updateUserEmailUseCase.execute(id, request.email());
        return ResponseEntity.noContent().build();
    }
}
