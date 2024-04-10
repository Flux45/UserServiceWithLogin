package com.scaler.userservice.dtos;

import com.scaler.userservice.models.role;
import com.scaler.userservice.models.user;
import jakarta.persistence.ManyToMany;

import java.util.List;

public class UserDto {
    private String name;
    private String email;
    @ManyToMany
    private List<role> roles;
    private boolean isEmailVerified;

    public static UserDto from(user user) {
        if (user == null) return null;

        UserDto userDto = new UserDto();
        userDto.email = user.getEmail();
        userDto.name = user.getName();
        userDto.roles = user.getRoles();
        userDto.isEmailVerified = user.isEmailVerified();

        return userDto;
    }
}
