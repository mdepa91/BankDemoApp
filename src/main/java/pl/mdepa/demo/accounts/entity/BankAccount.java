package pl.mdepa.demo.accounts.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BANK_ACCOUNTS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  BigDecimal balance;
}
