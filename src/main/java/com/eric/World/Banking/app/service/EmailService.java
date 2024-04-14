package com.eric.World.Banking.app.service;

import com.eric.World.Banking.app.payload.request.EmailDetails;

public interface EmailService {
  void sendEmailAlert(EmailDetails emailDetails);
  void sendEmailWithAttachment(EmailDetails emailDetails);
}
