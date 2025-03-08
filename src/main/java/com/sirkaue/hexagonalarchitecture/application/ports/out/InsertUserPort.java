package com.sirkaue.hexagonalarchitecture.application.ports.out;

import com.sirkaue.hexagonalarchitecture.domain.model.User;

public interface InsertUserPort {

    void insert(User user);
}
