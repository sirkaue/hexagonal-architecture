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
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.ErrorResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.UserResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.dto.response.ValidationErrorResponse;
import com.sirkaue.hexagonalarchitecture.infra.adapters.in.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Users", description = "Contains all operations related to a client's resource")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public final class UserController {

    private final UserMapper userMapper;
    private final InsertUserUseCase insertUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UpdateUserEmailUseCase updateUserEmailUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid domain data (e.g., invalid email format or password too short)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        User savedUser = insertUserUseCase.execute(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user's details by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable final Long id) {
        User user = findUserByIdUseCase.execute(id);
        UserResponse response = userMapper.toUserResponse(user);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update user email", description = "Updates the email address of an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Email updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid domain data (e.g., invalid email format)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Invalid email format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "New email already in use",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}/email")
    public ResponseEntity<Void> updateEmail(@PathVariable final Long id, @Valid @RequestBody UpdateEmailRequest request) {
        updateUserEmailUseCase.execute(id, request.email());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update user password", description = "Updates the password of an existing user after " +
            "validating the current password. Password must be at least 6 characters long.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid domain data (e.g., password too short)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error - password must be at least " +
                    "6 characters long",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Business rules violation - possible reasons: " +
                    "current password incorrect, passwords don't match, or new password same as current",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable final Long id, @Valid @RequestBody UpdatePasswordRequest request) {
        updateUserPasswordUseCase.execute(id, new Password(request.currentPassword()), new Password(request.newPassword()),
                new Password(request.confirmPassword()));
        return ResponseEntity.noContent().build();
    }
}
