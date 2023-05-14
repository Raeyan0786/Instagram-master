package com.example.instagram.dao;

import com.example.instagram.module.Token;
import com.example.instagram.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(User user);

    Token findFirstByToken(String token);
}
