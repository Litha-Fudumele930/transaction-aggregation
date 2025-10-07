package com.mycompany.myapp.service.Impl;

import com.mycompany.myapp.client.authRegistry.AuthRegistryApiClient;
import com.mycompany.myapp.service.AuthService;
import com.mycompany.myapp.service.api.dto.LoginRequest;
import com.mycompany.myapp.service.api.dto.LoginResponse;
import com.mycompany.myapp.service.api.dto.authRegistry.TokenRequest;
import com.mycompany.myapp.service.api.dto.authRegistry.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    AuthRegistryApiClient authRegistryApiClient;

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        // Call auth api for token
        LoginResponse loginResponse = new LoginResponse();
        TokenRequest tokenRequest = new TokenRequest();
        //build request
        tokenRequest.setUsername(loginRequest.getUsername());
        tokenRequest.setPassword(loginRequest.getPassword());

        try {
            log.debug("Calling registry");

            TokenResponse tokenResponse = authRegistryApiClient.getAuthToken(tokenRequest).getBody();

            log.debug("Registry response: {}", tokenResponse);

            // set response
            loginResponse.setAccessToken(tokenResponse.getIdToken());
        } catch (Exception e) {
            log.error("Authentication failed with error : {}", e.getMessage());

            throw new RuntimeException(e);
        }

        return loginResponse;
    }
}
