package com.example.demo.client;

import com.example.demo.entity.AuthGrantType;
import com.example.demo.entity.EnumGrantType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Client implements BaseClient {
    private String id;
    private String clientId;
    private String clientSecret;
    private Set<ClientAuthenticationMethod> authMethod = new HashSet<>();
    private Set<AuthorizationGrantType> authGrantType = new HashSet<>();
    private Set<String> scopes = new HashSet<>();
    private Set<String> redirectUris = new HashSet<>();

    public Client(String clientId, String clientSecret, Set<ClientAuthenticationMethod> authMethod,
                  Set<AuthorizationGrantType> grantType, Set<String> scopes, Set<String> redirectUris) {
        this.id = UUID.randomUUID().toString();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authMethod = authMethod;
        this.authGrantType = grantType;
        this.scopes = scopes;
        this.redirectUris = redirectUris;
    }

    public Client(){ }

    public String getId() {
        return id;
    }

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

    public Set<ClientAuthenticationMethod> getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(Set<ClientAuthenticationMethod> authMethod) {
        this.authMethod = authMethod;
    }

    public Set<AuthorizationGrantType> getAuthGrantType() {
        return authGrantType;
    }

    public void setAuthGrantType(Set<AuthorizationGrantType> authGrantType) {
        this.authGrantType = authGrantType;
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

    @Override
    public Set<String> getStringGrantTypes(){
        return authGrantType.stream()
                .map(AuthorizationGrantType::getValue)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getStringRedirectUris(){
        return this.redirectUris;
    }

    static class Builder{
        private String clientId;
        private String clientSecret;
        private Set<ClientAuthenticationMethod> authMethods = new HashSet<>();
        private Set<AuthorizationGrantType> grantTypes = new HashSet<>();
        private Set<String> scopes = new HashSet<>();
        private Set<String> redirectUris = new HashSet<>();

        Builder newBuilder(){
            return new Client.Builder();
        }

        Builder clientId(String id){
            this.clientId = id;
            return this;
        }

        Builder clientSecret(String secret){
            this.clientSecret = secret;
            return this;
        }

        Builder authenticationMethod(ClientAuthenticationMethod method){
            this.authMethods.add(method);
            return this;
        }

        Builder authorizationGrantType(AuthorizationGrantType type){
            this.grantTypes.add(type);
            return this;
        }

        Builder scope(String scope){
            this.scopes.add(scope);
            return this;
        }

        Builder redirectUri(String uri){
            this.redirectUris.add(uri);
            return this;
        }

        Client build(){
            Client client = new Client();

            client.clientId = this.clientId;
            client.clientSecret = this.clientSecret;
            client.authMethod = this.authMethods;
            client.authGrantType = this.grantTypes;
            client.scopes = this.scopes;
            client.redirectUris = this.redirectUris;
            return client;
        }
    }
}
