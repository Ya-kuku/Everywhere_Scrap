package com.example.backend.model.news;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "headline")
public class Headline {
    
    @Id
    private String hid;

    private String title;
    // 헤드라인 당일 날짜
    private String date;

}
