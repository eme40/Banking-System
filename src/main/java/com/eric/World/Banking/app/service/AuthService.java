package com.eric.World.Banking.app.service;

import com.eric.World.Banking.app.payload.request.LoginRequest;
import com.eric.World.Banking.app.payload.request.UserRequest;
import com.eric.World.Banking.app.payload.response.APIResponse;
import com.eric.World.Banking.app.payload.response.BankResponse;
import com.eric.World.Banking.app.payload.response.JwtAuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
  BankResponse registerUser(UserRequest userRequest);
  ResponseEntity<APIResponse<JwtAuthResponse>> login(LoginRequest loginRequest);
}
