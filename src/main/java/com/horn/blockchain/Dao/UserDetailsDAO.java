package com.horn.blockchain.Dao;

import com.horn.blockchain.chaincode.util.base.Result;
import com.horn.blockchain.model.UserDetails;
import org.apache.catalina.User;

import java.util.List;
import java.util.Map;

public interface UserDetailsDAO {

    //增
    boolean insertUserDetails(Map<String, String> message);

    //删
    boolean removeUserDetailsByName(String name);
    boolean removeUserDetailsByEmail(String email);

    //改
    boolean updateUserDetails(Map<String, String > message);

    //查
    UserDetails selectByName(String username);
    UserDetails selectByEmail(String email);
    List<UserDetails> selectByNameAndEmail(Map<String, String > message);
    List<UserDetails> selectAll(Integer pageNum, Integer pageSize);

}