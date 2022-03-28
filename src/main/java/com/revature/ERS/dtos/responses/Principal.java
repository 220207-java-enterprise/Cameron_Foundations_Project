package com.revature.ERS.dtos.responses;

import com.revature.ERS.models.Users;

public class Principal {

    private String user_id;
    private String username;
    private String role_id;

    public Principal() {
        super();
    }

    public Principal(Users user) {
        this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.role_id = user.getRole_id();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole(String role) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", role_id='" + role_id + '\'' +
                '}';
    }
}