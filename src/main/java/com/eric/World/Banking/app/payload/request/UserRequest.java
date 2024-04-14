package com.eric.World.Banking.app.payload.request;

import com.eric.World.Banking.app.domain.enums.Roles;
import com.eric.World.Banking.app.validation.ValidEmail;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
  @Size(min = 2, max = 35, message = "First name must be at least 2 characters")
  @NotBlank(message = "First name must not be empty")
  private String firstName;
  @Size(min = 2, max = 35, message = "Last name must be at least 2 characters")
  @NotBlank(message = "Last name must not be empty")
  private String lastName;
  private String middleName;

  @Size(min = 11, max = 15, message = "Phone Number is too short or too long")
  @NotBlank(message = "Phone number must not  be empty")
  @Digits(fraction = 0, integer = 11, message = "phone number is incorrect")
  private String phoneNumber;
  private String alternativePhoneNumber;
  private String stateOfOrigin;

  @NotBlank(message = "Email must not  be empty")
  @Email(message = "Invalid email")
  private String email;

  @NotBlank(message = "Password must not  be empty")
  private String password;
  private String address;
  private String gender;
}
