package com.daniel.geramed.service;

import com.daniel.geramed.model.request.AuthRequest;
import com.daniel.geramed.model.response.LoginResponse;
import com.daniel.geramed.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);

    RegisterResponse registerStore(AuthRequest request);

    RegisterResponse registerAdmin(AuthRequest request);

    LoginResponse login(AuthRequest request);

}
