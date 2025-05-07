package com.sirkaue.hexagonalarchitecture.domain.model;

import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Email;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;

public class User {

    private final Long id;
    private final String name;
    private final Email email;
    private final Password password;

    public User(Long id, String name, Email email, Password password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public User changePasswordTo(String newPassword) {
        return new User(this.id, this.name, this.email, new Password(newPassword));
    }

    public User changeEmailTo(String newEmail) {
        return new User(this.id, this.name, new Email(newEmail), this.password);
    }
}
