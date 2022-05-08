package com.example.demo.oauth.authCode;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

public class AuthorizationCode {
    private String clientId;
    private String redirectUri;
    private Date issuedAt;
    private Date expiredAt;
    private String code;
    private Set<String> scopes;

    public AuthorizationCode(String clientId, String redirectUri, Date issuedAt,
                             Date expiredAt, String code, Set<String> scopes) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.code = code;
        this.scopes = scopes;
    }

    public AuthorizationCode(Date expiredAt){
        this.expiredAt = expiredAt;
    }

    public AuthorizationCode(){ }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public boolean isExpired(){
      if(new Date().getTime() > this.expiredAt.getTime()) return true;
      return false;
    }

//    public static void main(String[] args) {
//        try {
//            AuthorizationCode code = new AuthorizationCode(new Date(new Date().getTime() + 1000000));
//            Thread.sleep(3000);
//            System.out.println(code.isExpired());
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }
}
