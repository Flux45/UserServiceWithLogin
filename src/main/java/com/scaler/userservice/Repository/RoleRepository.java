package com.scaler.userservice.Repository;

import com.scaler.userservice.models.role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<role,Long> {
}
