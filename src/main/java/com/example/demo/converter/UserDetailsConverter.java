package com.example.demo.converter;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.UserSignupRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserDetailsConverter extends BaseConverter<User, UserSignupRequest> {
    protected String name = "kirill";

    public UserDetailsConverter() {
        super(null, UserDetailsConverter::toEntity);
    }

    public static User toEntity(UserSignupRequest request) {
        return new User(request.getUsername(),
                request.getPassword(),
                request.getMail(), new HashSet<Role>());
    }
}
