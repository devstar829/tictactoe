package com.telusko.simpleWebApp.model;

public class UserTokenMessage {
    private String userId;
    private String token;

    // Constructor
    public UserTokenMessage(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
