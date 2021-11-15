package com.horn.blockchain.service.impl;


import com.horn.blockchain.Dao.UserDetailsDAO;
import com.horn.blockchain.chaincode.util.base.Result;
import com.horn.blockchain.chaincode.util.Results;
import com.horn.blockchain.model.UserDetails;
import com.horn.blockchain.service.LoginOrRegisterService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoginOrRegisterServiceImpl implements LoginOrRegisterService {

    @Autowired
    UserDetailsDAO userDetailsDAO;

    @Override
    public Result login(Map<String, String> message) {
        try{
            List<UserDetails> answer = userDetailsDAO.selectByNameAndEmail(message);
            if(answer != null && answer.size() > 0) return Results.success();
            return Results.failure();
        }catch (Exception e){
            return Results.failure(e.getMessage());
        }
    }

    @Override
    public Result register(Map<String, String> message) {
        try {
          if(userDetailsDAO.insertUserDetails(message)){
              return Results.success();
          }
          return Results.failure("insert failure");
        } catch (Exception e) {
            return Results.failure(e.getMessage());
        }
    }
}
