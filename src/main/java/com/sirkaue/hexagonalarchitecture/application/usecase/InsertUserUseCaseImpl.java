package com.sirkaue.hexagonalarchitecture.application.usecase;

import com.sirkaue.hexagonalarchitecture.application.ports.in.InsertUserUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.InsertUserPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UserExistsByEmailPort;
import com.sirkaue.hexagonalarchitecture.domain.exception.EmailAlreadyExistsException;
import com.sirkaue.hexagonalarchitecture.domain.model.User;

public class InsertUserUseCaseImpl implements InsertUserUseCase {

    private final InsertUserPort insertUserPort;
    private final UserExistsByEmailPort userExistsByEmailPort;

    public InsertUserUseCaseImpl(InsertUserPort insertUserPort, UserExistsByEmailPort userExistsByEmailPort) {
        this.insertUserPort = insertUserPort;
        this.userExistsByEmailPort = userExistsByEmailPort;
    }

    @Override
    public void execute(User user) {
        validateEmail(user.getEmail());
        insertUserPort.insert(user);
    }

    private void validateEmail(String email) {
        if (userExistsByEmailPort.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }
}
