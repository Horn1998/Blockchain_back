package com.horn.blockchain.Dao.impl;

import com.horn.blockchain.chaincode.util.base.Result;
import com.horn.blockchain.chaincode.util.Results;
import com.horn.blockchain.model.UserDetails;
import com.horn.blockchain.Dao.UserDetailsDAO;
import org.apache.catalina.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class UserDetailsImpl implements UserDetailsDAO {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public boolean insertUserDetails(Map<String, String> message) {
        try{
            UserDetails userDetails = new UserDetails();
            userDetails.setName(message.get("name"));
            userDetails.setPassword(message.get("pass"));
            userDetails.setEmail(message.get("email"));
            userDetails = mongoTemplate.insert(userDetails);
            if (userDetails != null) {
                return true;
            } else {
                return false;
            }
        }catch(Exception e){
            return false;
        }

    }

    @Override
    public boolean removeUserDetailsByName(String name) {
        try {
            Query query = new Query(Criteria.where("name").is(name));
            mongoTemplate.remove(query, UserDetails.class);
            return true;
        }catch (Exception e){
           return false;
        }
    }

    @Override
    public boolean removeUserDetailsByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        mongoTemplate.remove(query, UserDetails.class);
        try {
            mongoTemplate.remove(email);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateUserDetails(Map<String, String> message) {
        try{
            Query query = new Query(Criteria.where("name").is(message.get("name")));
            Update update = new Update();
            update.set("email", message.get("email"));
            update.set("password", message.get("password"));
            mongoTemplate.updateFirst(query, update, UserDetails.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public UserDetails selectByName(String username) {
        try{
            Query query = new Query(Criteria.where("name").is(username));
            UserDetails userDetails = mongoTemplate.findOne(query, UserDetails.class);
            return userDetails;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public UserDetails selectByEmail(String email) {
        try{
            Query query = new Query(Criteria.where("email").is(email));
            UserDetails userDetails = mongoTemplate.findOne(query, UserDetails.class);
            return userDetails;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<UserDetails> selectByNameAndEmail(Map<String, String> message) {
        try{
            Query query = Query.query(Criteria.where("name").is(message.get("name")).and("password").is(message.get("password")));
            List<UserDetails> passengers = mongoTemplate.find(query, UserDetails.class);
            return passengers;
        }catch (Exception e){
            return null;
        }

    }


    @Override
    public List<UserDetails> selectAll(Integer pageNum, Integer pageSize) {
        return null;
    }

}