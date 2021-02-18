package pl.mdepa.demo.accounts.controller.payloads;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountBalanceResponse {
  private BigDecimal balance;
  private BigDecimal freeAmount;
  private BigDecimal availableBalance;
}
