package com.example.backend.controller;

import java.util.ArrayList;
import java.util.Collections;
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
        ResponseEntity<Object> response = null;
        Collections.reverse(economy);   // 내림차순 정렬
        
        ArrayList<Object> allEconomy = new ArrayList<>();
        for (var i=0;i<economy.size();i++) {
            for (var j=0;j<=economy.get(i).getMain().size();j++) {
                allEconomy.add(economy.get(i).getMain().get(Integer.toString(j)));
            }
        }

        final BasicResponse result = new BasicResponse();
        result.object = allEconomy;
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
        result.object = economy.getHeadline();
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
        Collections.reverse(itscience);   // 내림차순 정렬
        
        ArrayList<Object> allItscience = new ArrayList<>();
        for (var i=0;i<itscience.size();i++) {
            for (var j=0;j<=itscience.get(i).getMain().size();j++) {
                allItscience.add(itscience.get(i).getMain().get(Integer.toString(j)));
            }
        }

        final BasicResponse result = new BasicResponse();
        result.object = allItscience;
        result.status = true;
        result.data = "IT/과학 기사가 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    
    @GetMapping("/news/itscience/headline")
    @ApiOperation(value="IT/과학 그날의 헤드라인 조회")
    public Object getItscienceHeadline(@RequestParam(required = true) final String date) {

        ItScience itscience = itScienceRepository.findByDate(date);
        ResponseEntity<Object> response = null;

        final BasicResponse result = new BasicResponse();
        result.object = itscience.getHeadline();
        result.status = true;
        result.data = date + "IT/과학 헤드라인이 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }


    @GetMapping("/news/society")
    @ApiOperation(value="사회 기사 조회")
    public Object getSociety() {

        List<Society> society = societyRepository.findAll();
        ResponseEntity<Object> response = null;
        Collections.reverse(society);   // 내림차순 정렬
        
        ArrayList<Object> allSociety = new ArrayList<>();
        for (var i=0;i<society.size();i++) {
            for (var j=0;j<=society.get(i).getMain().size();j++) {
                allSociety.add(society.get(i).getMain().get(Integer.toString(j)));
            }
        }

        final BasicResponse result = new BasicResponse();
        result.object = allSociety;
        result.status = true;
        result.data = "사회 기사가 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }


    @GetMapping("/news/society/headline")
    @ApiOperation(value="사회 그날의 헤드라인 조회")
    public Object getSocietyHeadline(@RequestParam(required = true) final String date) {

        Society society = societyRepository.findByDate(date);
        ResponseEntity<Object> response = null;

        final BasicResponse result = new BasicResponse();
        result.object = society.getHeadline();
        result.status = true;
        result.data = date + "사회 헤드라인이 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    // date
    @GetMapping("/news/economy/contents")
    @ApiOperation(value = "경제 뉴스 기사 보기")
    public Object getEconomyContent(@RequestParam(required = true) final String date, 
                                 @RequestParam(required = true) final String index ) {

        Economy economy = economyRepository.findByDate(date);
        ResponseEntity<Object> response = null;

        final BasicResponse result = new BasicResponse();
        result.object = economy.getMain().get(index);
        result.status = true;
        result.data = date + "경제 본문이 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/news/society/contents")
    @ApiOperation(value="사회 뉴스 기사 보기")
    public Object getSocietyContent(@RequestParam(required = true) final String date,
                                    @RequestParam(required = true) final String index ) {

        Society society = societyRepository.findByDate(date);
        ResponseEntity<Object> response = null;

        final BasicResponse result = new BasicResponse();
        result.object = society.getMain().get(index);
        result.status = true;
        result.data = date + "사회 본문이 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/news/itscience/contents")
    @ApiOperation(value="IT/과학 뉴스 기사 보기")
    public Object getItscienceContent(@RequestParam(required = true) final String date,
                                      @RequestParam(required = true) final String index) {

        ItScience itscience = itScienceRepository.findByDate(date);
        ResponseEntity<Object> response = null;

        final BasicResponse result = new BasicResponse();
        result.object = itscience.getMain().get(index);
        result.status = true;
        result.data = date + "IT/과학 본문이 조회되었습니다.";
        
        response =  new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    

                                 

}
