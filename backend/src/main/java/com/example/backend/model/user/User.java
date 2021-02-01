package com.example.backend.model.user;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "account")
public class User {
    
    @Id
    private String uid;

    private String email;
    
    private String password;

    private String nickname;

    private String phone;

}
