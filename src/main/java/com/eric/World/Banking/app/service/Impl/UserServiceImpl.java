package com.eric.World.Banking.app.service.Impl;

import com.eric.World.Banking.app.domain.entities.UserEntity;
import com.eric.World.Banking.app.payload.request.*;
import com.eric.World.Banking.app.payload.response.AccountInfo;
import com.eric.World.Banking.app.payload.response.BankResponse;
import com.eric.World.Banking.app.repository.UserRepository;
import com.eric.World.Banking.app.service.EmailService;
import com.eric.World.Banking.app.service.TransactionService;
import com.eric.World.Banking.app.service.UserService;
import com.eric.World.Banking.app.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final EmailService emailService;
  private final TransactionService transactionService;

  @Override
  public BankResponse creditAccount(CreditAndDebitRequest request) {
    // to credit an account , first check if the account exists
    boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
    if (!isAccountExists){
      return BankResponse.builder()
              .responseCode(AccountUtils.ACCOUNT_NUMBER_NOT_EXIST_CODE)
              .responseMessage(AccountUtils.ACCOUNT_NUMBER_NOT_FOUND_MESSAGE)
              .accountInfo(null)
              .build();
    }
    UserEntity userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
    userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));

    userRepository.save(userToCredit);

    EmailDetails creditAlert = EmailDetails.builder()
            .subject("CREDIT ALERT")
            .recipient(userToCredit.getEmail())
            .messageBody("Your account has been credited with # " + request.getAmount() +
                    " from " + userToCredit.getFirstName() + " Your current account balance is # " +
                    userToCredit.getAccountBalance())
            .build();

    emailService.sendEmailAlert(creditAlert);

    // save transfer transaction
    TransactionRequest transactionRequest = TransactionRequest.builder()
            .accountNumber(userToCredit.getAccountNumber())
            .TransactionType("CREDIT")
            .amount(request.getAmount())
            .build();
    transactionService.saveTransactions(transactionRequest);

    return BankResponse.builder()
            .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
            .accountInfo(AccountInfo.builder()
                    .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + userToCredit.getMiddleName())
                    .accountBalance(userToCredit.getAccountBalance())
                    .accountNumber(request.getAccountNumber())
                    .build())
            .build();
  }

  @Override
  public BankResponse debitAccount(CreditAndDebitRequest request) {

    // to credit an account , first check if the account exists
    boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
    if (!isAccountExists){
      return BankResponse.builder()
              .responseCode(AccountUtils.ACCOUNT_NUMBER_NOT_EXIST_CODE)
              .responseMessage(AccountUtils.ACCOUNT_NUMBER_NOT_FOUND_MESSAGE)
              .accountInfo(null)
              .build();
    }
    UserEntity userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
    BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
    BigInteger debitAmount = request.getAmount().toBigInteger();

    if (availableBalance.intValue() < debitAmount.intValue()){
      return BankResponse.builder()
              .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
              .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
              .accountInfo(null)
              .build();
    }
    userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
    userRepository.save(userToDebit);

    EmailDetails debitAlert = EmailDetails.builder()
            .subject("DEBIT ALERT")
            .recipient(userToDebit.getEmail())
            .messageBody("The sum of " + request.getAmount() + " has been deducted from your account! \n Your current account balance is " + userToDebit.getAccountBalance())
            .build();
    emailService.sendEmailAlert(debitAlert);

    // save transfer transaction
    TransactionRequest transactionRequest = TransactionRequest.builder()
            .accountNumber(userToDebit.getAccountNumber())
            .TransactionType("DEBIT")
            .amount(request.getAmount())
            .build();
    transactionService.saveTransactions(transactionRequest);

    return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
            .accountInfo(AccountInfo.builder()
                    .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getMiddleName())
                    .accountBalance(userToDebit.getAccountBalance())
                    .accountNumber(request.getAccountNumber())
                    .build())
            .build();
  }

  @Override
  public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
    // to credit an account , first check if the account exists
    boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
    if (!isAccountExists){
      return BankResponse.builder()
              .responseCode(AccountUtils.ACCOUNT_NUMBER_NOT_EXIST_CODE)
              .responseMessage(AccountUtils.ACCOUNT_NUMBER_NOT_FOUND_MESSAGE)
              .accountInfo(null)
              .build();
    }
    UserEntity foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

    return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_NUMBER_FOUND_CODE)
            .responseMessage(AccountUtils.ACCOUNT_NUMBER_FOUND_MESSAGE)
            .accountInfo(AccountInfo.builder()
                    .accountBalance(foundUser.getAccountBalance())
                    .accountNumber(enquiryRequest.getAccountNumber())
                    .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getMiddleName())
                    .build())
            .build();
  }

  public String nameEnquiry(EnquiryRequest enquiryRequest){
    boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

    if (!isAccountExists){
      return AccountUtils.ACCOUNT_NUMBER_NOT_FOUND_MESSAGE;
    }

    UserEntity foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
    return  foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getMiddleName();
  }

  @Override
  public BankResponse transfer(TransferRequest request) {
    boolean isDestinationAccountExists = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
    if (!isDestinationAccountExists){
      return BankResponse.builder()
              .responseCode(AccountUtils.ACCOUNT_NUMBER_NOT_EXIST_CODE)
              .responseMessage(AccountUtils.ACCOUNT_NUMBER_NOT_FOUND_MESSAGE)
              .accountInfo(null)
              .build();
    }

    UserEntity sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
    if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0){
      return  BankResponse.builder()
              .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
              .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
              .accountInfo(null)
              .build();
    }
    sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
    userRepository.save(sourceAccountUser);

    String sourceUserName = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName() + " " + sourceAccountUser.getMiddleName();

    EmailDetails debitAlert = EmailDetails.builder()
            .subject("DEBIT ALERT")
            .recipient(sourceAccountUser.getEmail())
            .messageBody("The sum of " + request.getAmount() + " has been deducted from your account! \n Your current account balance is " + sourceAccountUser.getAccountBalance())
            .build();
    emailService.sendEmailAlert(debitAlert);

    UserEntity destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
    destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
    userRepository.save(destinationAccountUser);

    EmailDetails creditAlert = EmailDetails.builder()
            .subject("CREDIT ALERT")
            .recipient(destinationAccountUser.getEmail())
            .messageBody("Your account has been credited with " + request.getAmount() + " from " + sourceUserName + " Your current account balance is "
            + destinationAccountUser.getAccountNumber())
            .build();
    emailService.sendEmailAlert(creditAlert);

    // save transfer transaction
    TransactionRequest transactionRequest = TransactionRequest.builder()
            .accountNumber(destinationAccountUser.getAccountNumber())
            .TransactionType("CREDIT")
            .amount(request.getAmount())
            .build();
    transactionService.saveTransactions(transactionRequest);



    return BankResponse.builder()
            .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
            .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
            .accountInfo(null)
            .build();
  }




}
