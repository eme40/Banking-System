package com.eric.World.Banking.app.payload.response;

import com.eric.World.Banking.app.utils.DataUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class APIResponse<T> {
  private String message;
  private T data;
  private String responseTime;

  public APIResponse(String message, T data) {
    this.message = message;
    this.data = data;
    this.responseTime = DataUtils.dataToString(LocalDateTime.now());
  }

  public APIResponse(String message) {
    this.message = message;
    this.responseTime = DataUtils.dataToString(LocalDateTime.now());
  }
}
