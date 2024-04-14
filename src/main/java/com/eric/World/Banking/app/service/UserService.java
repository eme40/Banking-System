package com.eric.World.Banking.app.service;

import com.eric.World.Banking.app.payload.request.CreditAndDebitRequest;
import com.eric.World.Banking.app.payload.request.EnquiryRequest;
import com.eric.World.Banking.app.payload.request.TransferRequest;
import com.eric.World.Banking.app.payload.response.BankResponse;

public interface UserService {
  BankResponse creditAccount(CreditAndDebitRequest request);
  BankResponse debitAccount(CreditAndDebitRequest request);

  BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

  String nameEnquiry(EnquiryRequest enquiryRequest);

  BankResponse transfer(TransferRequest request);
}
