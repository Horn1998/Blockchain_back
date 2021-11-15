package com.horn.blockchain.service;

import com.horn.blockchain.chaincode.util.base.Result;

import java.util.Map;

public interface LoginOrRegisterService {

    Result register(Map<String, String> message);

    Result login(Map<String, String> message);

}
