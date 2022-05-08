package com.example.demo.converter;

import com.example.demo.client.Client;
import com.example.demo.client.ClientDto;
import com.example.demo.entity.ClientEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONObject;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ClientDetailsConverter extends BaseConverter<Client, ClientDto> {
    public ClientDetailsConverter(){
       super(ClientDetailsConverter::convertToDto, ClientDetailsConverter::convertToEntity);
    }

     private static ClientDto convertToDto(Client client){
         return new ClientDto();
    }

    private static Client convertToEntity(ClientDto dto){
        Client client = new Client();
        client.setClientId(dto.getClientId());
        client.setClientSecret(dto.getClientSecret());
        client.setAuthMethod(convertAuthMethods(dto.getAuthMethods()));
        client.setAuthGrantType(convertGrantTypes(dto.getGrantTypes()));
        client.setScopes(dto.getScopes());
        client.setRedirectUris(dto.getRedirectUris());
        return client;

//        ClientEntity entity = new ClientEntity();
//        entity.setClientId(dto.getClientId());
//        entity.setClientSecret(dto.getClientSecret());

    }



//    private static ClientEntity convertToEntity(ClientDto clientDto){
//
//    }

    private static Set<ClientAuthenticationMethod> convertAuthMethods(Set<String> authMethods) {
        Set<ClientAuthenticationMethod> clientAuthenticationMethods = new HashSet<>();
        authMethods.forEach(method -> {
            clientAuthenticationMethods.add(new ClientAuthenticationMethod(method));
        });
        return clientAuthenticationMethods;
    }

    private static Set<AuthorizationGrantType> convertGrantTypes(Set<String> grantTypes){
        Set<AuthorizationGrantType> authorizationGrantTypes = new HashSet<>();
        grantTypes.forEach(type -> {
            authorizationGrantTypes.add(new AuthorizationGrantType(type));
        });
        return authorizationGrantTypes;
    }

}
