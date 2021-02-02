package com.example.backend.repository.news;

import java.util.List;

import com.example.backend.model.news.Society;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SocietyRepository extends MongoRepository<Society, String> {

    Society findByDate(String Date);

    List<Society> findAll();
}
