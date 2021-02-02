package com.example.backend.repository.news;

import java.util.List;

import com.example.backend.model.news.Economy;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface EconomyRepository extends MongoRepository<Economy, String> {

    Economy findByDate(String date);

    List<Economy> findAll();
}
