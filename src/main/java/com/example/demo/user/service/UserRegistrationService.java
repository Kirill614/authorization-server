package com.example.demo.user.service;

import com.example.demo.payload.UserSignupRequest;

public interface UserRegistrationService {
    void registerNewAccount(UserSignupRequest request, boolean isAdmin);
}
