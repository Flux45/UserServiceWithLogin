package com.example.userservice.service;

import com.example.userservice.Repository.RoleRepository;
import com.example.userservice.Repository.TokenRepository;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.exception.UserNotFountException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Primary
@Service("SelfUserService")
public class SelfUserService implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SelfUserService(UserRepository userRepository,RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder,TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token login(String email, String password) throws UserNotFountException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()) {
            throw new UserNotFountException("User with email: " + email + " doesn't exists");
        }

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())) {
            throw new UserNotFountException("Password entered is incorrect");
        }

        Token token = new Token();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date dateIn30Days = calendar.getTime();
        token.setExpiryAt(dateIn30Days);
        token.setUser(user);


        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Token savedToken = tokenRepository.save(token);

        return savedToken;
    }

    @Override
    public Void logout(String token) {
        Optional<Token> token1 = tokenRepository.findByValueAndDeleted(token, false);
        if (token1.isEmpty()) {
            // throw TokenNotExistsOrAlreadyExpiredException
        }

        Token tkn = token1.get();
        tkn.setDeleted(true);
        tokenRepository.save(tkn);
        return null;
    }


    @Override
    public User signUp(String fullName, String email, String password) {
        User u = new User();
        u.setEmail(email);
        u.setName(fullName);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User user = userRepository.save(u);
        return user;
    }

    @Override
    public User validateToken(String token) {
        Optional<Token> tkn = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(token,false, new Date());

        if (tkn.isEmpty()) {
            return null;
        }

        return tkn.get().getUser();
    }
}
