package com.eric.World.Banking.app.infrastructure.controller;

import com.eric.World.Banking.app.domain.entities.Transaction;
import com.eric.World.Banking.app.service.Impl.BankStatementImpl;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/statement")
@RequiredArgsConstructor
public class TransactionController {
  private final BankStatementImpl bankStatement;
  @GetMapping
  public List<Transaction> generateStatement(@RequestParam String accountNumber,
                                             @RequestParam String startDate,
                                             @RequestParam String endDate)
          throws DocumentException, FileNotFoundException {
    return bankStatement.generateStatement(accountNumber,startDate,endDate);

  }
}
