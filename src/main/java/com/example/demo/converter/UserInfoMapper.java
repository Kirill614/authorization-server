package com.example.demo.converter;

import com.example.demo.entity.User;
import com.example.demo.payload.UserInfoResponse;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper {
    public UserInfoResponse mapToDto(User user){
        return new UserInfoResponse(user.getUsername(), user.getMail());
    }
}
