package com.example.backend.model.news;

import lombok.Data;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "itscience")
public class ItScience {
    
    // @Id
    // private String nid;

    // private String title;

    // private String date;

    // private String url;
    
    // private String description;

    @Id
    private String nid;

    private String date;
    
    private Map<String, Object> headline;

    private Map<String, Object> main;

}
