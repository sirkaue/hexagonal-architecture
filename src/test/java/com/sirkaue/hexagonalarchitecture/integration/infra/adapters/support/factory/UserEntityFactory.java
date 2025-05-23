package com.sirkaue.hexagonalarchitecture.integration.infra.adapters.support.factory;

import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;

public class UserEntityFactory {

    public static UserEntity create(String name, String email, String password) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static UserEntity defaultUser() {
        return create("John Doe", "john@example.com", "password123");
    }
}
