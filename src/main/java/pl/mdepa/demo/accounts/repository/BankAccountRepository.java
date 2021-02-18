package pl.mdepa.demo.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.mdepa.demo.accounts.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
