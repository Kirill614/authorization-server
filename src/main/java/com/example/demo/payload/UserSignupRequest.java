package com.example.demo.payload;

public class UserSignupRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String mail;

    public UserSignupRequest(String username, String password, String confirmPassword,
                             String mail) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
