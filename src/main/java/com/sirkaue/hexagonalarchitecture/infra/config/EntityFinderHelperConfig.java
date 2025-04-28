package com.sirkaue.hexagonalarchitecture.infra.config;

import com.sirkaue.hexagonalarchitecture.application.helper.EntityFinderHelper;
import com.sirkaue.hexagonalarchitecture.application.ports.out.FindUserByIdPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityFinderHelperConfig {

    @Bean
    public EntityFinderHelper entityFinderHelper(FindUserByIdPort findUserByIdPort) {
        return new EntityFinderHelper(findUserByIdPort);
    }
}
