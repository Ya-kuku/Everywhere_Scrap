package com.example.backend.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

import com.example.backend.model.user.User;


@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secretkey}")
    private String jwtSecretKey; 

    private Long jwtExpmin = 1000 * 60L * 60L * 2L; // 2시간


    // JWT Token 생성
    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUid());
        claims.put("user", user);

        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims) // 클레임 설정
            .setSubject(user.getUid()) // 토큰 용도
            .setIssuedAt(now) // 토큰 발행 시간 정보
            .setExpiration(new Date(now.getTime() + jwtExpmin)) // TOKEN 유효기간 설정
            .signWith(SignatureAlgorithm.HS256, jwtSecretKey)  // 사용할 암호화 알고리즘, signature 에 들어갈 secret값 세팅
            .compact();
    }


    // JWT Token 으로 User조회
    public String getUser(String Token) {
        // try {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(Token).getBody().getSubject();
        // } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
        //     System.out.println(e);
        // } catch (Exception e) { // 그외 에러났을 경우
        //     System.out.println(e);
        // }
    }


}
