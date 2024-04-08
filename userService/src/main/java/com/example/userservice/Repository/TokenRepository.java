package com.example.userservice.Repository;


import com.example.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;


@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Token save(Token token);

    Optional<Token> findByValueAndDeleted(String value,boolean isDeleted);

    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String value, boolean isDeleted, Date expiryGreaterThan);
}
