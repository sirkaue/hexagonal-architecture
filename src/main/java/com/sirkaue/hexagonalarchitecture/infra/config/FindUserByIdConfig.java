package com.sirkaue.hexagonalarchitecture.infra.config;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.in.FindUserByIdUseCase;
import com.sirkaue.hexagonalarchitecture.application.usecase.FindUserByIdUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindUserByIdConfig {

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(EntityFinderHelper entityFinderHelper) {
        return new FindUserByIdUseCaseImpl(entityFinderHelper);
    }
}
