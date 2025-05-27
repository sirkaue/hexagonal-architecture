package com.sirkaue.hexagonalarchitecture.infra.adapters.in.web.controller;

import com.sirkaue.hexagonalarchitecture.application.ports.in.FindUserByIdUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.in.InsertUserUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.in.UpdateUserEmailUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.in.UpdateUserPasswordUseCase;
import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.request.UpdateEmailRequest;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.request.UpdatePasswordRequest;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.request.UserRequest;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.UserResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final InsertUserUseCase insertUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UpdateUserEmailUseCase updateUserEmailUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        User savedUser = insertUserUseCase.execute(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
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

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable final Long id, @RequestBody UpdatePasswordRequest request) {
        updateUserPasswordUseCase.execute(id, new Password(request.currentPassword()), new Password(request.newPassword()),
                new Password(request.confirmPassword()));
        return ResponseEntity.noContent().build();
    }
}
