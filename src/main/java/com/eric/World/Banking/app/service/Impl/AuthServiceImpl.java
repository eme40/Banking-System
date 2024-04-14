package com.eric.World.Banking.app.service.Impl;

import com.eric.World.Banking.app.domain.entities.UserEntity;
import com.eric.World.Banking.app.infrastructure.config.JwtTokenProvider;
import com.eric.World.Banking.app.payload.request.EmailDetails;
import com.eric.World.Banking.app.payload.request.LoginRequest;
import com.eric.World.Banking.app.payload.request.UserRequest;
import com.eric.World.Banking.app.payload.response.AccountInfo;
import com.eric.World.Banking.app.payload.response.APIResponse;
import com.eric.World.Banking.app.payload.response.BankResponse;
import com.eric.World.Banking.app.payload.response.JwtAuthResponse;
import com.eric.World.Banking.app.repository.UserRepository;
import com.eric.World.Banking.app.service.AuthService;
import com.eric.World.Banking.app.service.EmailService;
import com.eric.World.Banking.app.utils.AccountUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
@Builder
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final EmailService emailService;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder encoder;
  @Override
  public BankResponse registerUser(UserRequest userRequest) {
    if (userRepository.existsByEmail(userRequest.getEmail())){
      return BankResponse.builder()
              .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
              .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
              .accountInfo(null)
              .build();
    }
    UserEntity newUser = UserEntity.builder()
            .firstName(userRequest.getFirstName())
            .lastName(userRequest.getLastName())
            .middleName(userRequest.getMiddleName())
            .gender(userRequest.getGender())
            .address(userRequest.getAddress())
            .stateOfOrigin(userRequest.getStateOfOrigin())
            .accountNumber(AccountUtils.generateAccountNumber())
            .accountBalance(BigDecimal.ZERO)
            .email(userRequest.getEmail())
            .password(userRequest.getPassword())
            .phoneNumber(encoder.encode(userRequest.getPassword()))
            .phoneNumber(userRequest.getPhoneNumber())
            .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
            .status("Active")
            .profilePicture("https://res.cloudinary.com/dpfqbb9pl/image/upload/v1701260428/maleprofile_ffeep9.png")
            .build();

    UserEntity saveUser = userRepository.save(newUser);

    // send email alert
    EmailDetails emailDetails = EmailDetails.builder()
            .recipient(saveUser.getEmail())
            .subject("ACCOUNT CREATION")
            .messageBody("CONGRATULATIONS!!! Your account has been successfully created. \n Your Account Details: \n +" +
                    " Account Name: " + saveUser.getFirstName() + " " + saveUser.getLastName() + " "+
                    "\n Account Number: " + saveUser.getAccountNumber())
            .build();

    return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
            .accountInfo(AccountInfo.builder()
                    .accountBalance(saveUser.getAccountBalance())
                    .accountNumber(saveUser.getAccountNumber())
                    .accountName(saveUser.getFirstName() +  " " +
                            saveUser.getLastName() + " " +
                            saveUser.getMiddleName())
                    .build())
            .build();
  }

  @Override
  public ResponseEntity<APIResponse<JwtAuthResponse>> login(LoginRequest loginRequest) {
    Optional<UserEntity> userEntityOptional = userRepository.findByEmail(loginRequest.getEmail());

    Authentication authentication = null;

    authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );

    EmailDetails loginAlert = EmailDetails.builder()
            .subject("You are logged in")
            .recipient(loginRequest.getEmail())
            .messageBody("You logged into your account. if you did not initiate this request, contact")
            .build();
    emailService.sendEmailAlert(loginAlert);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtTokenProvider.generateToken(authentication);

    UserEntity userEntity = userEntityOptional.get();

    return ResponseEntity.status(HttpStatus.OK)
            .body(
                    new APIResponse<>(
                            "login successful",
                            JwtAuthResponse.builder()
                                    .accessToken(token)
                                    .tokenType("Bearer")
                                    .id(userEntity.getId())
                                    .email(userEntity.getEmail())
                                    .gender(userEntity.getGender())
                                    .firstName(userEntity.getFirstName())
                                    .lastName(userEntity.getLastName())
                                    .profilePicture(userEntity.getProfilePicture())
                                    .roles(userEntity.getRoles())
                                    .build()
                    )
            );
  }
}
