package com.scaler.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class user extends baseModel {
    private String name;
    private String email;
    private String hashedPassword;
    @ManyToMany
    private List<role> roles;
    private boolean isEmailVerified;

}
