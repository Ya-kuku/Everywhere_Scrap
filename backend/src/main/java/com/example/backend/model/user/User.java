package com.example.backend.model.user;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "account")
public class User {
    
    @Id
    private String uid;

    private String email;
    private String password;

    private String nickname;
    private String phone;

    private LocalDateTime createDate;

}
