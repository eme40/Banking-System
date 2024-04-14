package com.eric.World.Banking.app.service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eric.World.Banking.app.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
  private final Cloudinary cloudinary;
  @Override
  public String uploadFile(MultipartFile multipartFile) throws IOException {
    return cloudinary.uploader()
            .upload(multipartFile.getBytes(), ObjectUtils.emptyMap())
            .get("secure_url")
            .toString();
  }
}
