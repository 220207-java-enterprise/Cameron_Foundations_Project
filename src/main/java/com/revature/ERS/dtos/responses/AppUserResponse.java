package com.revature.ERS.dtos.responses;

import com.revature.ERS.models.Users;

public class AppUserResponse {

    private String user_id;
    private String username;
    private String given_name;
    private String surname;
    private String role_id;

    public AppUserResponse() {
        super();
    }

    public AppUserResponse(Users user) {
        this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.given_name = user.getGiven_name();
        this.surname = user.getSurname();
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

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}