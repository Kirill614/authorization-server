package com.example.demo.user.service;

import com.example.demo.entity.User;
import com.example.demo.payload.UserSignupRequest;

public interface UserRegistrationService {
    User registerNewAccount(UserSignupRequest request, boolean isAdmin);
}
