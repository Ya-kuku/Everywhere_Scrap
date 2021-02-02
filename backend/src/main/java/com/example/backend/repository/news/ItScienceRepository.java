package com.example.backend.repository.news;

import java.util.List;

import com.example.backend.model.news.ItScience;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItScienceRepository extends MongoRepository<ItScience, String> {

    ItScience findByDate(String Date);

    List<ItScience> findAll();
}
