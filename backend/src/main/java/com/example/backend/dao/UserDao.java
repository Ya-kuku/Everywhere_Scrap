package com.example.backend.dao;

import java.util.Optional;

import com.example.backend.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User, String> {

    User getUserByEmail(String email);
    User getUserByNickname(String nickname);

    Optional<User> findUserByEmailAndPassword(String email, String password);


}
