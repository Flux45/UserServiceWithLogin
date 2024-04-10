package com.scaler.userservice.Repository;

import com.scaler.userservice.models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<user, Long> {

    user save(user user);

    Optional<user> findByEmail(String email);

}
