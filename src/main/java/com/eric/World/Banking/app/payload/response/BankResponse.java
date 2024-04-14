package com.eric.World.Banking.app.payload.response;

import com.eric.World.Banking.app.domain.entities.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BankResponse<T> {
  private String responseCode;
  private String responseMessage;
  private AccountInfo accountInfo;

  public BankResponse(String responseCode, String responseMessage, AccountInfo accountInfo) {
    this.responseCode = responseCode;
    this.responseMessage = responseMessage;
    this.accountInfo = accountInfo;
  }

  public BankResponse(String responseMessage, ErrorDetails errorDetails) {
    this.responseMessage = responseMessage;
  }

  public BankResponse(String message, String fileUrl) {
    this.responseMessage = message;
  }

  public BankResponse(String message) {
    this.responseMessage = message;
  }
}
