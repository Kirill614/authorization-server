package com.example.demo.user.service;

import com.example.demo.entity.User;
import com.example.demo.user.UserDetailsImpl;
import com.example.demo.user.dao.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private JpaUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.existsByUsername(username)) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(""));
            return UserDetailsImpl.build(user);
        }
        return null;
    }

    public boolean existsByMail(String mail){
        return userRepository.existsByMail(mail);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

}
