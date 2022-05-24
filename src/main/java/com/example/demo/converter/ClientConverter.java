package com.example.demo.converter;

import com.example.demo.client.Client;
import com.example.demo.client.ClientDto;
import com.example.demo.entity.*;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ClientConverter extends BaseConverter<ClientEntity, ClientDto> {
    public ClientConverter() {
        super(ClientConverter::convertToDto, ClientConverter::convertToEntity);
    }

    public static ClientDto convertToDto(ClientEntity client) {
        ClientDto dto = new ClientDto();
        dto.setGrantTypes(convertGrantTypes(client.getAuthGrantTypes()));
        dto.setAuthMethods(new HashSet<>(Arrays.asList(
                EnumAuthMethod.CLIENT_SECRET_BASIC.name())));
        dto.setScopes(convertScopes(client.getScopes()));
        dto.setRedirectUris(convertRedirectUris(client.getRedirectUris()));
        dto.setClientId(client.getClientId());
        dto.setClientSecret(client.getClientSecret());
        return dto;
    }

    public static ClientEntity convertToEntity(ClientDto clientDto){
        ClientEntity client = new ClientEntity();
        client.setClientId(clientDto.getClientId());
        client.setClientSecret(clientDto.getClientSecret());
        clientDto.getScopes().forEach(scope -> client.getScopes()
                .add(new Scope(scope)));
        clientDto.getRedirectUris().forEach((uri -> client.getRedirectUris()
                .add(new RedirectUri(uri))));
        return client;
    }

    private static Set<String> convertGrantTypes(Set<AuthGrantType> grantTypes) {
        return grantTypes.stream()
                .map(type -> type.getGrantType().name())
                .collect(Collectors.toSet());
    }

    private static Set<String> convertScopes(Set<Scope> scope){
        return scope.stream()
                .map(Scope::getScope)
                .collect(Collectors.toSet());
    }

    private static Set<String> convertRedirectUris(Set<RedirectUri> uris){
        return uris.stream()
                .map(RedirectUri::getUri)
                .collect(Collectors.toSet());
    }

}
