package com.example.demo.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class ClientDto {
    @NotBlank
    private String clientId;
    @NotBlank
    private String clientSecret;
    @NotNull
    private Set<String> authMethods;
    @NotNull
    private Set<String> grantTypes;
    @NotNull
    private Set<String> scopes;
    @NotNull
    private Set<String> redirectUris;

    public ClientDto(String clientId, String clientSecret, Set<String> authMethods,
                     Set<String> grantTypes, Set<String> scopes, Set<String> redirectUris) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authMethods = authMethods;
        this.grantTypes = grantTypes;
        this.scopes = scopes;
        this.redirectUris = redirectUris;
    }

    public ClientDto(){ }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Set<String> getAuthMethods() {
        return authMethods;
    }

    public void setAuthMethods(Set<String> authMethods) {
        this.authMethods = authMethods;
    }

    public Set<String> getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(Set<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public Set<String> getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(Set<String> redirectUris) {
        this.redirectUris = redirectUris;
    }
}
