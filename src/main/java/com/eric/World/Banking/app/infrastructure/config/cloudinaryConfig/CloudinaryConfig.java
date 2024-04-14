package com.eric.World.Banking.app.infrastructure.config.cloudinaryConfig;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
  private final  String CLOUD_NAME = "ddad0j57l";
  private final String API_KEY = "296894574971396";
  private final String API_SECRET = "k9TNW-pLQWgtrZTDv7AP8CtKXqw";

  @Bean
  public Cloudinary cloudinary(){
    Map<String, String> config = new HashMap<>();

    config.put("cloud_name", CLOUD_NAME);
    config.put("api_key",API_KEY);
    config.put("api-secret",API_SECRET);
    return new Cloudinary(config);
  }

}
