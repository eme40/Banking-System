package com.eric.World.Banking.app.infrastructure.exception;

import com.eric.World.Banking.app.domain.entities.ErrorDetails;
import com.eric.World.Banking.app.payload.response.BankResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(EmailNotSendException.class)
  public ResponseEntity<BankResponse<ErrorDetails>> handleEmailNotSendException(final EmailNotSendException ex){
    ErrorDetails errorDetails = ErrorDetails.builder()
            .message(ex.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .debugMessage("Email Not Sent")
            .build();
    BankResponse<ErrorDetails> response = new BankResponse<>(ex.getMessage(),errorDetails);

    return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
