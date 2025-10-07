package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.AuthService;
import com.mycompany.myapp.service.api.dto.LoginRequest;
import com.mycompany.myapp.service.api.dto.LoginResponse;
import com.mycompany.myapp.web.api.AuthApiDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthResource implements AuthApiDelegate {

    private final Logger log = LoggerFactory.getLogger(AuthResource.class);

    @Autowired
    AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> authenticateUser(LoginRequest loginRequest) {
        log.info("Authenticating user {} ", loginRequest.getUsername());

        // Call the auth service
        LoginResponse response = authService.authenticateUser(loginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
