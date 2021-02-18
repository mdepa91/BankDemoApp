package pl.mdepa.demo.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.mdepa.demo.accounts.model.AccountBalance;
import pl.mdepa.demo.accounts.service.CalculateAvailableBalance;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountBalanceController {
  @Autowired
  private CalculateAvailableBalance calculateAvailableBalance;

  @GetMapping("/balance/{accountId}")
  public AccountBalance getCurrentAccountBalance(@PathVariable("accountId") long accountId) {
    return calculateAvailableBalance.calculate(accountId);
  }

  @GetMapping("/balance")
  public AccountBalance getCurrentBankBalance() {
    return calculateAvailableBalance.calculate();
  }
}
