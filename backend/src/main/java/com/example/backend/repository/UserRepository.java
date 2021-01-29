package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import com.example.backend.model.user.User;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    User findByNickname(String nickname);
    
    User findByUid(String uid);

    List<User> findAll();

    Optional<User> findUserByEmail(String email);
}
