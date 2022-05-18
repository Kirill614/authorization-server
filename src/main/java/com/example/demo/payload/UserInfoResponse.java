package com.example.demo.payload;

public class UserInfoResponse {
    private String username;
    private String mail;

    public UserInfoResponse() {}

    public UserInfoResponse(String username, String mail) {
        this.username = username;
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
