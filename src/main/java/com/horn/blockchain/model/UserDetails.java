package com.horn.blockchain.model;

import org.springframework.data.mongodb.core.mapping.Document;

// @Document的collection属性设置的是在mongo库中的集合名称
@Document(collection = "register")
public class UserDetails {

    private String name;
    private String password;
    private String _id;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}