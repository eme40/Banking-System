package com.eric.World.Banking.app.service.Impl;

import com.eric.World.Banking.app.payload.request.EmailDetails;
import com.eric.World.Banking.app.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender javaMailSender;
  @Value("${spring.mail.username}")
  private  String senderEmail;
  @Override
  public void sendEmailAlert(EmailDetails emailDetails) {
  try{
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    simpleMailMessage.setFrom(senderEmail);
    simpleMailMessage.setTo(emailDetails.getRecipient());
    simpleMailMessage.setText(emailDetails.getMessageBody());
    simpleMailMessage.setSubject(emailDetails.getSubject());

    javaMailSender.send(simpleMailMessage);
    System.out.println("Mail sent successfully");
  } catch (Exception e) {
    throw new RuntimeException(e);
  }
  }

  @Override
  public void sendEmailWithAttachment(EmailDetails emailDetails) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper;
    try {
      mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
      mimeMessageHelper.setFrom(senderEmail);
      mimeMessageHelper.setTo(emailDetails.getRecipient());
      mimeMessageHelper.setTo(emailDetails.getMessageBody());
      mimeMessageHelper.setText(emailDetails.getSubject());

      FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
      mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
      javaMailSender.send(mimeMessage);

      log.info(file.getFilename() + " has been sent to user with email " + emailDetails.getRecipient());
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
