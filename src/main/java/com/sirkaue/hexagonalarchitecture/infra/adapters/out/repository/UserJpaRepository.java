package com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository;

import com.sirkaue.hexagonalarchitecture.infra.adapters.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
}
