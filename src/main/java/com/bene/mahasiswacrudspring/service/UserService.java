package com.bene.mahasiswacrudspring.service;

import com.bene.mahasiswacrudspring.dto.AuthResponse;
import com.bene.mahasiswacrudspring.dto.LoginRequest;
import com.bene.mahasiswacrudspring.dto.RegisterRequest;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
