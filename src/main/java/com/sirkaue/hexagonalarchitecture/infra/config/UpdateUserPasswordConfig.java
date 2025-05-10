package com.sirkaue.hexagonalarchitecture.infra.config;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.UpdateUserPasswordUseCase;
import com.sirkaue.hexagonalarchitecture.application.ports.out.PasswordEncoderPort;
import com.sirkaue.hexagonalarchitecture.application.ports.out.UpdateUserPort;
import com.sirkaue.hexagonalarchitecture.application.usecase.UpdateUserPasswordUseCaseImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateUserPasswordConfig {

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(EntityFinderHelper entityFinderHelper, PasswordEncoderPort passwordEncoderPort,
                                                               @Qualifier("updateUserPasswordAdapter") UpdateUserPort updateUserPort) {
        return new UpdateUserPasswordUseCaseImpl(entityFinderHelper, passwordEncoderPort, updateUserPort);
    }
}
