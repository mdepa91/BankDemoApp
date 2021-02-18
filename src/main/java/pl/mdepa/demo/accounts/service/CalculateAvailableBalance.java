package pl.mdepa.demo.accounts.service;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import pl.mdepa.demo.accounts.entity.BankAccount;
import pl.mdepa.demo.accounts.model.AccountBalance;
import pl.mdepa.demo.accounts.repository.BankAccountRepository;

@Slf4j
@Service
public class CalculateAvailableBalance {

  @Autowired
  private FreeAmountService freeAmountService;
  @Autowired
  private RandomAmountService randomAmountService;
  @Autowired
  private BankAccountRepository bankAccountRepository;

  public AccountBalance calculate() {
    return bankAccountRepository.findAll().stream()
        .map(BankAccount::getBalance)
        .map(balance -> new AccountBalance(balance, getFreeAmount()))
        .reduce(new AccountBalance(), AccountBalance::sum);
  }

  public AccountBalance calculate(long accountId) {
    BigDecimal freeAmount = getFreeAmount();
    BigDecimal balance = bankAccountRepository.findById(accountId)
        .map(BankAccount::getBalance)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found any account"));
    return new AccountBalance(balance, freeAmount);
  }

  private BigDecimal getFreeAmount() {
    try {
      return freeAmountService.getFreeAmount();
    } catch (RuntimeException e) {
      log.error(ExceptionUtils.getMessage(e), e);
      return randomAmountService.getFreeAmount();
    }
  }
}
