package com.horn.blockchain.services;

import com.horn.base.Result;
import com.horn.blockchain.model.UserDetails;

import java.util.Map;

public interface UserDetailsService {
    Map<String, Object> selectByUsername(String username);

    boolean updateUserDetails(UserDetails userDetails);

    boolean insertUserDetails(UserDetails userDetails) throws Exception;

    boolean hasEmail(String email);

    Result registerUser(Map<String, String> message);

    Map<String, Object> selectAll(Integer pageNum, Integer pageSize);

    String selectUserIdByEmail(String email) throws Exception;

    UserDetails selectUserDetailsByUsernameOrEmail(String usernameOrEmail);
}