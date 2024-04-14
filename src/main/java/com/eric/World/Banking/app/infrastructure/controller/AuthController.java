package com.eric.World.Banking.app.infrastructure.controller;

import com.eric.World.Banking.app.payload.request.LoginRequest;
import com.eric.World.Banking.app.payload.request.UserRequest;
import com.eric.World.Banking.app.payload.response.APIResponse;
import com.eric.World.Banking.app.payload.response.BankResponse;
import com.eric.World.Banking.app.payload.response.JwtAuthResponse;
import com.eric.World.Banking.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "User Authentication Management APIs")
public class AuthController {
  private final AuthService authService;

  @Operation(
          summary = "Register New User Account",
          description = "create a new user and account number"
  )
  @PostMapping("/register-user")
  public BankResponse creationAccount(@Valid @RequestBody UserRequest userRequest){
    return authService.registerUser(userRequest);
  }
  @PostMapping("login-user")
  public ResponseEntity<APIResponse<JwtAuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
    return authService.login(loginRequest);
  }
}
