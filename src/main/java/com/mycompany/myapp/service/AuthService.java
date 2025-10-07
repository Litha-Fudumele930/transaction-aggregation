package com.mycompany.myapp.service;

import com.mycompany.myapp.service.api.dto.LoginRequest;
import com.mycompany.myapp.service.api.dto.LoginResponse;

public interface AuthService {
    LoginResponse authenticateUser(LoginRequest loginRequest);
}
