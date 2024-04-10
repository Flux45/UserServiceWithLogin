package com.scaler.userservice.Repository;


import com.scaler.userservice.models.token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;


@Repository
public interface TokenRepository extends JpaRepository<token,Long> {

    token save(token token);

    Optional<token> findByValueAndDeleted(String value, boolean isDeleted);

    Optional<token> findByValueAndDeletedAndExpiryAtGreaterThan(String value, boolean isDeleted, Date expiryGreaterThan);
}
