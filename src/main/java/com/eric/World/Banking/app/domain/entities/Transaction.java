package com.eric.World.Banking.app.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String transactionId;
  private String transactionType;
  private String accountNumber;
  private BigDecimal amount;
  private String status;
  @CreationTimestamp
  private LocalDate createdAt;
  @UpdateTimestamp
  private LocalDate modifiedAt;
}
