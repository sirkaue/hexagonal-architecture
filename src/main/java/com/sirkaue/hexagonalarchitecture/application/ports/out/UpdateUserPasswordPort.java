package com.sirkaue.hexagonalarchitecture.application.ports.out;

import com.sirkaue.hexagonalarchitecture.domain.model.User;

public interface UpdateUserPasswordPort {

    User update(User user);
}
