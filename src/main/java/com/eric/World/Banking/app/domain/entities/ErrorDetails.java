package com.eric.World.Banking.app.domain.entities;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetails {
  private String message;
  private String debugMessage;
  private HttpStatus status;
  private String dateTime;
}
