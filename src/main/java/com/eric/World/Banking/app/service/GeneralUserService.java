package com.eric.World.Banking.app.service;

import com.eric.World.Banking.app.payload.response.BankResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface GeneralUserService {
  ResponseEntity<BankResponse<String>> uploadProfilePics(MultipartFile multipartFile);
}
