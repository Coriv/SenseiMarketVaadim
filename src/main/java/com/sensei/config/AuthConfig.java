package com.sensei.config;

import com.sensei.dto.AuthDto;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

public class AuthConfig {

    private static AuthConfig authConfig;
    private AuthConfig() {
    }

    public static AuthConfig getInstance() {
        if(authConfig == null){
            authConfig = new AuthConfig();
        }
        return authConfig;
    }

    public HttpHeaders getRequestAuthenticate(AuthDto authDto) {
        String authData =  authDto.getUsername() + ":" + authDto.getPassword();
        byte[] authDataBytes = authData.getBytes();
        byte[] base64authData = Base64.encodeBase64(authDataBytes);
        String base64Creds = new String(base64authData);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
    }
}
