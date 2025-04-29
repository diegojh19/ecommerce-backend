package com.icodeap.ecommerce.backend.infrastructure.adapter;

import com.icodeap.ecommerce.backend.domain.model.User;
import com.icodeap.ecommerce.backend.infrastructure.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface IUserCrudRepository  extends CrudRepository<UserEntity, Integer> {
   Optional<UserEntity> findByEmail(String email);
   Optional<UserEntity> findByTokenPassword(String tokenPassword);


   boolean existsByEmail(String email);

  // El método findByVerificationToken se usa para buscar un usuario a través del token de verificación.
     UserEntity findByVerificationToken(String token);
}
