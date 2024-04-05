package com.scaler.userservice.service;

import com.scaler.userservice.exception.UserNotFountException;
import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;

public interface UserService {
    User signUp(String fullName, String email, String password);
    Token login(String email, String password) throws UserNotFountException;

    Void logout(String token);

    User validateToken(String token);
}
