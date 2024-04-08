package com.example.userservice.service;

import com.example.userservice.exception.UserNotFountException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;


public interface UserService {
    User signUp(String fullName, String email, String password);

    Token login(String email, String password) throws UserNotFountException;

    Void logout(String token);

    User validateToken(String token);

}
