package com.eric.World.Banking.app.service.Impl;

import com.eric.World.Banking.app.domain.entities.Transaction;
import com.eric.World.Banking.app.payload.request.TransactionRequest;
import com.eric.World.Banking.app.repository.TransactionRepository;
import com.eric.World.Banking.app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepository transactionRepository;

  @Override
  public void saveTransactions(TransactionRequest transactionRequest) {
    Transaction transaction = Transaction.builder()
            .transactionType(transactionRequest.getTransactionType())
            .accountNumber(transactionRequest.getAccountNumber())
            .amount(transactionRequest.getAmount())
            .status("SUCCESS")
            .build();
    transactionRepository.save(transaction);
    System.out.println("Transaction saved successfully");
  }
}
