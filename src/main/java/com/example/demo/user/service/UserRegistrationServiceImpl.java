package com.example.demo.user.service;

import com.example.demo.converter.UserDetailsConverter;
import com.example.demo.entity.EnumRole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exceptions.RoleNotFoundException;
import com.example.demo.payload.UserSignupRequest;
import com.example.demo.user.dao.JpaRoleRepository;
import com.example.demo.user.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private JpaRoleRepository roleRepository;
    private UserDetailsConverter converter;
    private UserDetailsServiceImpl userDetailsService;
    private PasswordEncoder encoder;

    public UserRegistrationServiceImpl(UserDetailsServiceImpl userDetailsService,
                                       JpaRoleRepository roleRepository,
                                       UserDetailsConverter converter,
                                       PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.converter = converter;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    public User registerNewAccount(UserSignupRequest request, boolean isAdmin) {
        Role role;
        if(isAdmin){
            role = roleRepository.findByRole(EnumRole.ROLE_ADMIN)
               .orElseThrow(() -> new RoleNotFoundException(EnumRole.ROLE_USER.name()));
        } else {
            role = roleRepository.findByRole(EnumRole.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException(EnumRole.ROLE_USER.name()));
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setMail(request.getMail());
        user.getRoles().add(role);
        return userDetailsService.saveUser(user);
    }

}
