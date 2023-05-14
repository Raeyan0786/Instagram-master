package com.example.instagram.service;

import com.example.instagram.dao.TokenRepository;
import com.example.instagram.module.Token;
import com.example.instagram.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository iTokenRepo;


    public void saveToken(Token token) {
        iTokenRepo.save(token);
    }

    public Token getToken(User user) {
        return iTokenRepo.findByToken(user);

    }

    public boolean authenticate(String userEmail, String token) {

        Token authToken = iTokenRepo.findFirstByToken(token);//find token object via token string
        String expectedEmail = authToken.getUser().getUserEmail();
        return expectedEmail.equals(userEmail);

    }
}
