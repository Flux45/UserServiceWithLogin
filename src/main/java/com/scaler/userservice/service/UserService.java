package com.scaler.userservice.service;

import com.scaler.userservice.exception.UserNotFountException;
import com.scaler.userservice.models.token;
import com.scaler.userservice.models.user;

public interface UserService {
    user signUp(String fullName, String email, String password);
    token login(String email, String password) throws UserNotFountException;

    Void logout(String token);

    user validateToken(String token);
}
