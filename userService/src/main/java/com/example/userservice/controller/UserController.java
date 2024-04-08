package com.example.userservice.controller;

import com.example.userservice.Repository.TokenRepository;
import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.LogoutRequestDto;
import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.exception.UserNotFountException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.service.UserService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private RestTemplate restTemplate;
    private final TokenRepository tokenRepository;

    @Autowired
    public UserController(@Qualifier("SelfUserService") UserService userService,RestTemplate restTemplate,
                          TokenRepository tokenRepository) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFountException {
        // check if email and password in db
        // if yes return user
        // else throw some error
        return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto requestDto) {
        // no need to hash password for now
        // just store user as is in the db
        // for now no need to have email verification either
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String name = requestDto.getName();
        return userService.signUp(name,email,password);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestDto) {
        // delete token if exists -> 200
        // if doesn't exist give a 404

        userService.logout(requestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") @NotNull String token) {
        return UserDto.from(userService.validateToken(token));
    }
}
