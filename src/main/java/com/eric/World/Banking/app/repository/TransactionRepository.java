package com.eric.World.Banking.app.repository;

import com.eric.World.Banking.app.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
}
