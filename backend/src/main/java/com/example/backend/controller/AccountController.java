package com.example.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.example.backend.model.BasicResponse;
import com.example.backend.model.news.Economy;
import com.example.backend.model.news.ItScience;
import com.example.backend.model.news.Society;
import com.example.backend.model.user.SignupRequest;
import com.example.backend.model.user.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.news.EconomyRepository;
import com.example.backend.repository.news.ItScienceRepository;
import com.example.backend.repository.news.SocietyRepository;
import com.example.backend.security.JwtTokenProvider;
import com.example.backend.security.PasswordEncoding;
import com.example.backend.security.JwtAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class AccountController {

        @Autowired
        UserRepository userRepository;

        @Autowired
        EconomyRepository economyRepository;

        @Autowired
        SocietyRepository societyRepository;

        @Autowired
        ItScienceRepository itScienceRepository;
        
        @Autowired
        JwtTokenProvider tokenProvider;


        @GetMapping("/account/login")
        @ApiOperation(value="로그인")
        public Object login(@RequestParam(required=true) final String email,
                            @RequestParam(required=true) final String password) {
                             
                Optional<User> userOpt = userRepository.findUserByEmail(email);
                ResponseEntity<Object> response = null;
                
                final BasicResponse result = new BasicResponse();
                PasswordEncoding passwordEncoding = new PasswordEncoding();

                if (userOpt.isPresent() && passwordEncoding.matches(password, userOpt.get().getPassword())) {

                        User user = new User();
                        user.setUid(userOpt.get().getUid());
                        user.setEmail(email);
                        user.setPassword(password);

                        String jwt = tokenProvider.createToken(user);

                        result.object = new JwtAuthentication(jwt);
                        result.status = true;
                        result.data = "로그인 되었습니다.";
                        response =  new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                        result.status = false;
                        result.data = "로그인에 실패하였습니다.";
                        response =  new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
                }
                
                return response;
        }


        @GetMapping("/account")
        public Object allUser() {
        
                List<User> userOpt = userRepository.findAll();

                ResponseEntity<Object> response = null;
                response = new ResponseEntity<>(userOpt, HttpStatus.OK);
                return response;
        }


        @PostMapping("/account/signup")
        @ApiOperation(value = "가입하기")
        public Object signup(@Valid @RequestBody final SignupRequest request, BindingResult bindingresult) {
                if (bindingresult.hasErrors()) {
                        ResponseEntity<Object> response = null;
                        final BasicResponse result = new BasicResponse();
                        result.status = false;
                        List<ObjectError> list = bindingresult.getAllErrors();
                        for (ObjectError e:list) {
                                result.data = e.getDefaultMessage();
                        }
                        response = new ResponseEntity< >(result, HttpStatus.FORBIDDEN);
                        return response;
                } else {

                        final List<User> email_check = userRepository.findByEmail(request.getEmail());
                        final List<User> nickname_check = userRepository.findByNickname(request.getNickname());
                        ResponseEntity<Object> response = null;
                        if(!email_check.isEmpty()) {
                                final BasicResponse result = new BasicResponse();
                                result.status = false;
                                result.data = "이미 사용중인 이메일 입니다.";
                                response = new ResponseEntity< >(result, HttpStatus.FORBIDDEN);
                        } else if(!nickname_check.isEmpty()) {
                                final BasicResponse result = new BasicResponse();
                                result.status = false;
                                result.data = "이미 사용중인 닉네임 입니다.";
                                response = new ResponseEntity< >(result, HttpStatus.FORBIDDEN);
                        } else {
                                PasswordEncoding passwordEncoding = new PasswordEncoding();

                                String newPassword = passwordEncoding.encode(request.getPassword());

                                Map<String, Map<String,Object>> likenews = new HashMap<String, Map<String,Object>>();
                                final User user = new User();
                                user.setEmail(request.getEmail());
                                user.setPassword(newPassword);
                                user.setNickname(request.getNickname());
                                user.setPhone(request.getPhone());
                                likenews.put("Economy", new HashMap<String, Object>());
                                likenews.put("Society", new HashMap<String, Object>());
                                likenews.put("ItScience", new HashMap<String, Object>());
                                user.setLikenews(likenews);
                                
                                userRepository.save(user);
                                
                                final BasicResponse result = new BasicResponse();
                                result.status = true;
                                result.data = "회원가입을 축하합니다.";
                                response = new ResponseEntity< >(result, HttpStatus.OK);
                        }
                        return response;
                }
        }


        @GetMapping("/account/profile")
        @ApiOperation(value = "회원 정보 조회")
        public Object profile(@RequestParam(required=true) final String Token) {
                
                String userId = tokenProvider.getUser(Token);
                List<User> user = userRepository.findByUid(userId);

                ResponseEntity<Object> response = null;
                
                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "회원 정보 조회 성공";
                result.object = user;
                
                response =  new ResponseEntity<>(result, HttpStatus.OK);
                
                return response;
        }


        
        @DeleteMapping("/account/delete")
        @ApiOperation(value = "계정삭제")
        public Object delete(String uid) {

                ResponseEntity<Object> response = null;
                userRepository.deleteById(uid);

                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "계정이 성공적으로 삭제되었습니다.";
                response = new ResponseEntity<>(result, HttpStatus.OK);
                
                return response;
        }


        @GetMapping("/account/likenews")
        @ApiOperation(value = "찜하기")
        public Object likeNews(@RequestParam(required=true) final String Token,
                        @RequestParam(required=true) final String url){

                ResponseEntity<Object> response = null;
                String userId = tokenProvider.getUser(Token);
                List<User> user = userRepository.findByUid(userId);
                
                List<Economy> economy = economyRepository.findAll();
                for (var i=0;i<economy.size();i++) {
                        for (var j=0;j<economy.get(i).getMain().size();j++) {
                                if (economy.get(i).getMain().get(String.valueOf(j)).toString().contains(url)) {
                                        if (!user.get(0).getLikenews().get("Economy").isEmpty()) {
                                                Set<String> keys = user.get(0).getLikenews().get("Economy").keySet();
                                                for (String key:keys) {
                                                        if (!user.get(0).getLikenews().get("Economy").get(key).toString().contains(url)) {
                                                                user.get(0).getLikenews().get("Economy").put(Integer.toString((int)(Math.random()*10000)), economy.get(i).getMain().get(Integer.toString(j)));
                                                                break;
                                                        } else {
                                                                break;
                                                        }
                                                } 
                                        } else {
                                                user.get(0).getLikenews().get("Economy").put(Integer.toString((int)(Math.random()*10000)), economy.get(i).getMain().get(Integer.toString(j)));
                                                break;
                                        }
                                }
                        }
                }
                List<ItScience> itscience = itScienceRepository.findAll();
                for (var i=0;i<itscience.size();i++) {
                        for (var j=0;j<itscience.get(i).getMain().size();j++) {
                                if (itscience.get(i).getMain().get(String.valueOf(j)).toString().contains(url)) {
                                        if (!user.get(0).getLikenews().get("ItScience").isEmpty()) {
                                                Set<String> keys = user.get(0).getLikenews().get("ItScience").keySet();
                                                for (String key:keys) {
                                                        if (!user.get(0).getLikenews().get("ItScience").get(key).toString().contains(url)) {
                                                                user.get(0).getLikenews().get("ItScience").put(Integer.toString((int)(Math.random()*10000)), itscience.get(i).getMain().get(Integer.toString(j)));
                                                                break;
                                                        } else {
                                                                break;
                                                        }
                                                } 
                                        } else {
                                                user.get(0).getLikenews().get("ItScience").put(Integer.toString((int)(Math.random()*10000)), itscience.get(i).getMain().get(Integer.toString(j)));
                                                break;
                                        }
                                }
                        }
                }
                List<Society> society = societyRepository.findAll();
                for (var i=0;i<society.size();i++) {
                        for (var j=0;j<society.get(i).getMain().size();j++) {
                                if (society.get(i).getMain().get(Integer.toString(j)).toString().contains(url)) {
                                        if (!user.get(0).getLikenews().get("Society").isEmpty()) {
                                                Set<String> keys = user.get(0).getLikenews().get("Society").keySet();
                                                for (String key:keys) {
                                                        if (!user.get(0).getLikenews().get("Society").get(key).toString().contains(url)) {
                                                                user.get(0).getLikenews().get("Society").put(Integer.toString((int)(Math.random()*10000)), society.get(i).getMain().get(Integer.toString(j)));
                                                                break;
                                                        } else {
                                                                break;
                                                        }
                                                } 
                                        } else {
                                                user.get(0).getLikenews().get("Society").put(Integer.toString((int)(Math.random()*10000)), society.get(i).getMain().get(Integer.toString(j)));
                                                break;
                                        }
                                }
                        }
                }

                this.userRepository.saveAll(user);

                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "찜하기 성공";
                result.object = user;
                
                response =  new ResponseEntity<>(result, HttpStatus.OK);
                return response;
        }

        @GetMapping("/account/likenews/delete")
        @ApiOperation(value = "찜한 것 삭제")
        public Object likeNewsDelete(@RequestParam(required=true) final String Token,
                        @RequestParam(required=true) final String url){

                String userId = tokenProvider.getUser(Token);
                List<User> user = userRepository.findByUid(userId);

                Set<String> keys1 = user.get(0).getLikenews().get("Economy").keySet();
                for (String key:keys1) {
                        if (user.get(0).getLikenews().get("Economy").get(key).toString().contains(url)) {
                                user.get(0).getLikenews().get("Economy").remove(key);
                                break;
                        }
                }

                Set<String> keys2 = user.get(0).getLikenews().get("ItScience").keySet();
                for (String key:keys2) {
                        if (user.get(0).getLikenews().get("ItScience").get(key).toString().contains(url)) {
                                user.get(0).getLikenews().get("ItScience").remove(key);
                                break;
                        }
                } 


                Set<String> keys3 = user.get(0).getLikenews().get("Society").keySet();
                for (String key:keys3) {
                        if (user.get(0).getLikenews().get("Society").get(key).toString().contains(url)) {
                                user.get(0).getLikenews().get("Society").remove(key);
                                break;
                        }
                } 
                
                this.userRepository.saveAll(user);
                ResponseEntity<Object> response = null;

                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "찜 삭제 완료";
                response =  new ResponseEntity<>(result, HttpStatus.OK);
                return response;
        }


        @GetMapping("/account/likenews/find")
        @ApiOperation(value = "찜한 것 조회")
        public Object likeNewsFind(@RequestParam(required=true) final String Token,
                        @RequestParam(required=true) final String url){
                
                String userId = tokenProvider.getUser(Token);
                List<User> user = userRepository.findByUid(userId);
                
                Boolean flag = false;
                
                if (user.get(0).getLikenews().get("Economy").size() != 0) {
                        Set<String> keys1 = user.get(0).getLikenews().get("Economy").keySet();
                        for (String key:keys1) {
                                if (user.get(0).getLikenews().get("Economy").get(key).toString().contains(url)) {
                                        flag = true;
                                        break;
                                }
                        }
                }
                if (user.get(0).getLikenews().get("Society").size() != 0) {
                        Set<String> keys3 = user.get(0).getLikenews().get("Society").keySet();
                        for (String key:keys3) {
                                if (user.get(0).getLikenews().get("Society").get(key).toString().contains(url)) {
                                        flag = true;
                                        break;
                                }
                        } 
                }
                if (user.get(0).getLikenews().get("ItScience").size() != 0) {
                        Set<String> keys2 = user.get(0).getLikenews().get("ItScience").keySet();
                        for (String key:keys2) {
                                if (user.get(0).getLikenews().get("ItScience").get(key).toString().contains(url)) {
                                        flag = true;
                                        break;
                                }
                        } 
                } 
                
                ResponseEntity<Object> response = null;

                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "찜 조회 완료";
                result.object = flag;
                response =  new ResponseEntity<>(result, HttpStatus.OK);
                return response;
        
        }







}
