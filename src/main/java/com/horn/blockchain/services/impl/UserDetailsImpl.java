package com.horn.blockchain.services.impl;

import com.horn.base.Result;
import com.horn.blockchain.services.UserDetailsService;
import com.horn.blockchain.model.UserDetails;
import com.horn.blockchain.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Map<String, Object> selectByUsername(String username) {
        return null;
    }

    @Override
    public boolean updateUserDetails(UserDetails userDetails) {
        return false;
    }

    @Override
    public boolean insertUserDetails(UserDetails userDetails) throws Exception {
        return false;
    }

    @Override
    public boolean hasEmail(String email) {
        return false;
    }

    @Override
    public Map<String, Object> selectAll(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public String selectUserIdByEmail(String email) throws Exception {
        return null;
    }

    @Override
    public UserDetails selectUserDetailsByUsernameOrEmail(String usernameOrEmail) {
        return null;
    }

    @Override
    public Result registerUser(Map<String, String> message) {
        try {
            System.out.println("yes run mongo");
            UserDetails userDetails = new UserDetails();
            userDetails.setName(message.get("name"));
            userDetails.setPassword(message.get("pass"));
            userDetails.setEmail(message.get("email"));
            userDetails = mongoTemplate.insert(userDetails);
            if (userDetails != null) {
                return Results.success();
            } else {
                return Results.failure("userDetails is null");
            }
        } catch (Exception e) {
            return Results.failure(e.getMessage());
        }

    }
}