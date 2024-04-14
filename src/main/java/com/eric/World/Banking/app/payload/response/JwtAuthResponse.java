package com.eric.World.Banking.app.payload.response;

import com.eric.World.Banking.app.domain.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtAuthResponse {
  private Long id;
  private String firstName;
  private String lastName;
  private String profilePicture;
  private String email;
  private String gender;
  private Roles roles;
  private String accessToken;
  private String tokenType = "Bearer";
}
