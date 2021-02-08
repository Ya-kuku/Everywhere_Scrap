package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import com.example.backend.model.user.User;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByEmail(String email);
    List<User> findByNickname(String nickname);
    
    List<User> findByUid(String uid);

    List<User> findAll();

    Optional<User> findUserByEmail(String email);
}
