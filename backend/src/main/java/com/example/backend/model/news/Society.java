package com.example.backend.model.news;

import lombok.Data;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "society")
public class Society {
    
    @Id
    private String nid;

    private String date;
    
    private Map<String, Object> headline;

    private Map<String, Object> main;
}
