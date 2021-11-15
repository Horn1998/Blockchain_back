package com.horn.blockchain.controller;

import com.horn.blockchain.chaincode.util.base.Result;
import com.horn.blockchain.chaincode.util.Results;
import com.horn.blockchain.Dao.UserDetailsDAO;
import com.horn.blockchain.service.LoginOrRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private LoginOrRegisterService loginOrRegisterService;

    @RequestMapping(value="/register-for-client", method = RequestMethod.POST)
    public Result rfclient(@RequestBody Map<String, String> resData) {
        try{
            return loginOrRegisterService.register(resData);
        }catch (Exception e){
            return Results.failure(e.getMessage());
        }
    }

    @RequestMapping(value="/register-for-miner", method = RequestMethod.POST)
    public Result rfMiner(@RequestBody Map<String, String> resData) {
        try{
            return  loginOrRegisterService.register(resData);
        }catch (Exception e){
            return Results.failure(e.getMessage());
        }
    }

    @RequestMapping(value="/login-for-miner", method = RequestMethod.POST)
    public Result lfMiner(@RequestBody Map<String, String> resData){
        try{
            return loginOrRegisterService.login(resData);
        }catch (Exception e){
            return Results.failure(e.getMessage());
        }
    }


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