package com.sirkaue.hexagonalarchitecture.application.ports.in;

import com.sirkaue.hexagonalarchitecture.domain.model.User;
import com.sirkaue.hexagonalarchitecture.domain.valueobjects.Password;

public interface UpdateUserPasswordUseCase {

    User execute(Long id, Password currentPassword, Password newPassword, Password confirmPassword);
}
