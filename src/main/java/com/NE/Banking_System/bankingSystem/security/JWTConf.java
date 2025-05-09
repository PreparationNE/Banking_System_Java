package com.NE.Banking_System.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTConf {
    @Value("${jwt.secret}")
    private String secret;
    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return 1000*60*60*5;
    }
}
