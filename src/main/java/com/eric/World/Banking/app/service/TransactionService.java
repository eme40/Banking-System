package com.eric.World.Banking.app.service;

import com.eric.World.Banking.app.payload.request.TransactionRequest;

public interface TransactionService {
  void saveTransactions(TransactionRequest transactionRequest);
}
