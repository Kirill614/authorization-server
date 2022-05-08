package com.example.demo.client;

import com.example.demo.entity.AuthGrantType;
import com.example.demo.entity.EnumGrantType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import javax.persistence.MappedSuperclass;
import java.util.Set;


public interface BaseClient {
     Set<String> getStringGrantTypes();
     Set<String> getStringRedirectUris();
}
