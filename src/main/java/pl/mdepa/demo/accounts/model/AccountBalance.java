package pl.mdepa.demo.accounts.model;

import java.math.BigDecimal;
import java.util.function.Function;

import org.apache.commons.lang3.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalance {
  private BigDecimal balance;
  private BigDecimal freeAmount;
  private BigDecimal availableBalance;

  public AccountBalance(BigDecimal balance, BigDecimal freeAmount) {
    this.balance = balance;
    this.freeAmount = freeAmount;
    this.availableBalance = balance.add(freeAmount).max(BigDecimal.ZERO);
  }

  public static AccountBalance sum(AccountBalance ab0, AccountBalance ab1) {
    return AccountBalance.builder()
        .balance(sumValues(ab0, ab1, AccountBalance::getBalance))
        .freeAmount(sumValues(ab0, ab1, AccountBalance::getFreeAmount))
        .availableBalance(sumValues(ab0, ab1, AccountBalance::getAvailableBalance))
        .build();
  }

  private static BigDecimal sumValues(AccountBalance ab0, AccountBalance ab1, Function<AccountBalance, BigDecimal> extract) {
    BigDecimal value0 = ObjectUtils.defaultIfNull(extract.apply(ab0), BigDecimal.ZERO);
    BigDecimal value1 = ObjectUtils.defaultIfNull(extract.apply(ab1), BigDecimal.ZERO);
    return value0.add(value1);
  }
}
