package com.horn.blockchain.controller;

import com.horn.base.Result;
import com.horn.blockchain.util.Results;
import com.horn.blockchain.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value="/insert", method = RequestMethod.POST)
    public Result insert(@RequestBody Map<String, String> resData) {
        try{
            return userDetailsService.registerUser(resData);
        }catch (Exception e){
            return Results.failure(e.getMessage());
        }

    }

//    @RequestMapping(value = "/loginQuery", method = RequestMethod.POST)
//    public Result query(@RequestBody UserDetails userDetails){
//        try {
//            Query query = Query.query(Criteria.where("name").is(userDetails.getName()).and("password").is(userDetails.getPassword()));
//            List<UserDetails> passengers = mongoTemplate.find(query, UserDetails.class);
//            if(passengers.size() > 0) return Results.success();
//            return Results.failure();
//        } catch (Exception e){
//            return Results.failure(e.getMessage());
//        }
//    }

//
//    @RequestMapping("/update")
//    public String update() {
//        Query query = Query.query(Criteria.where("password").is("world1"));
//        Update update = new Update();
//        update.set("name", "world2");
//        update.set("password", "world2");
//        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetails.class);
//        return "success";
//    }
//
//    @RequestMapping("/remove")
//    public String remove() {
//        Query query = Query.query(Criteria.where("password").is("world1"));
//        DeleteResult remove = mongoTemplate.remove(query, UserDetails.class);
//        return "success";
//    }
}