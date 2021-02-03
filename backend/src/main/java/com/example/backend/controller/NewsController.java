package com.example.backend.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.model.BasicResponse;
import com.example.backend.model.news.Economy;
import com.example.backend.model.news.ItScience;
import com.example.backend.model.news.Society;
import com.example.backend.repository.news.EconomyRepository;
import com.example.backend.repository.news.ItScienceRepository;
import com.example.backend.repository.news.SocietyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Bad Request", response = BasicResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) 
})

@CrossOrigin(origins = { "*" })
@RestController
public class NewsController {

    @Autowired
    EconomyRepository economyRepository;

    @Autowired
    SocietyRepository societyRepository;

    @Autowired
    ItScienceRepository itScienceRepository;


    @GetMapping("/news/all")
    @ApiOperation(value="기사 전체 조회")
    public Object getAllNews(@RequestParam(required = true) final String date) {
        
        Economy economy = economyRepository.findByDate(date);
        ItScience itscience = itScienceRepository.findByDate(date);
        Society society = societyRepository.findByDate(date);

        ArrayList<Object> allnews = new ArrayList<>();
        for (var i=0;i<20;i++) {
            allnews.add(economy.getMain().get(Integer.toString(i)));
            allnews.add(itscience.getMain().get(Integer.toString(i)));
            allnews.add(society.getMain().get(Integer.toString(i)));
        }
        
        ResponseEntity<Object> response = null;
        
        final BasicResponse result = new BasicResponse();
        result.object = allnews;
        result.status = true;
        result.data = "기사 전체가 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;


    }


    @GetMapping("/news/economy")
    @ApiOperation(value="경제 기사 조회")
    public Object getEconomy() {

        List<Economy> economy = economyRepository.findAll();
        // Economy economy = economyRepository.findByDate(date);
        ResponseEntity<Object> response = null;
        // System.out.println(economy);

        final BasicResponse result = new BasicResponse();
        result.object = economy;
        result.status = true;
        result.data = "경제 기사가 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/news/economy/headline")
    @ApiOperation(value="경제 그날의 헤드라인 조회")
    public Object getEconomyHeadline(@RequestParam(required = true) final String date) {

        // List<Economy> economy = economyRepository.findAll();
        Economy economy = economyRepository.findByDate(date);
        ResponseEntity<Object> response = null;

        final BasicResponse result = new BasicResponse();
        result.object = economy;
        result.status = true;
        result.data = date + "경제 헤드라인이 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/news/itscience")
    @ApiOperation(value="it/과학 기사 조회")
    public Object getItScience() {
        
        List<ItScience> itscience = itScienceRepository.findAll();
        ResponseEntity<Object> response = null;

        final BasicResponse result = new BasicResponse();
        result.object = itscience;
        result.status = true;
        result.data = "IT/과학 기사가 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/news/society")
    @ApiOperation(value="사회 기사 조회")
    public Object getSociety() {

        List<Society> society = societyRepository.findAll();
        ResponseEntity<Object> response = null;

        final BasicResponse result = new BasicResponse();
        result.object = society;
        result.status = true;
        result.data = "사회 기사가 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }


}
