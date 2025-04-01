package com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository;

import com.sirkaue.hexagonalarchitecture.infra.adapters.out.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
