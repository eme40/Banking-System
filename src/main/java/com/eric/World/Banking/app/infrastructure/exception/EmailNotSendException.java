package com.eric.World.Banking.app.infrastructure.exception;

public class EmailNotSendException extends RuntimeException{
  public EmailNotSendException(String message){
    super(message);
  }
}
