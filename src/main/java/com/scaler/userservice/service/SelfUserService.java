package com.scaler.userservice.service;

import com.scaler.userservice.Repository.RoleRepository;
import com.scaler.userservice.Repository.TokenRepository;
import com.scaler.userservice.Repository.UserRepository;
import com.scaler.userservice.exception.UserNotFountException;
import com.scaler.userservice.models.token;
import com.scaler.userservice.models.user;
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
    public token login(String email, String password) throws UserNotFountException {
        Optional<user> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()) {
            throw new UserNotFountException("User with email: " + email + " doesn't exists");
        }

        user user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())) {
            throw new UserNotFountException("Password entered is incorrect");
        }

        token token = new token();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date dateIn30Days = calendar.getTime();
        token.setExpiryAt(dateIn30Days);
        token.setUser(user);


        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        com.scaler.userservice.models.token savedToken = tokenRepository.save(token);

        return savedToken;
    }

    @Override
    public Void logout(String token) {
        Optional<com.scaler.userservice.models.token> token1 = tokenRepository.findByValueAndDeleted(token, false);
        if (token1.isEmpty()) {
            // throw TokenNotExistsOrAlreadyExpiredException
        }

        com.scaler.userservice.models.token tkn = token1.get();
        tkn.setDeleted(true);
        tokenRepository.save(tkn);
        return null;
    }


    @Override
    public user signUp(String fullName, String email, String password) {
        user u = new user();
        u.setEmail(email);
        u.setName(fullName);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));

        user user = userRepository.save(u);
        return user;
    }

    @Override
    public user validateToken(String token) {
        Optional<com.scaler.userservice.models.token> tkn = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(token,false, new Date());

        if (tkn.isEmpty()) {
            return null;
        }

        return tkn.get().getUser();
    }
}
