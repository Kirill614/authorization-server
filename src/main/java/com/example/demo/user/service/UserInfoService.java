package com.example.demo.user.service;

import com.example.demo.payload.UserInfoResponse;

public interface UserInfoService {
    UserInfoResponse getUserInfo(String username);
}
