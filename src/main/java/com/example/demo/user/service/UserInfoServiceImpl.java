package com.example.demo.user.service;

import com.example.demo.converter.UserInfoMapper;
import com.example.demo.entity.User;
import com.example.demo.payload.UserInfoResponse;
import com.example.demo.user.dao.JpaUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    private JpaUserRepository userRepository;
    private UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(JpaUserRepository userRepository,
                               UserInfoMapper userInfoMapper) {
        this.userRepository = userRepository;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public UserInfoResponse getUserInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return userInfoMapper.mapToDto(user);
    }
}
