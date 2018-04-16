package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.repositories.implMock.AccountRepositoryImplMock;
import com.epam.aviasales.services.AccountService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {

  private static final AccountService instance = new AccountServiceImpl();

  public static AccountService getInstance() {
    return instance;
  }

  private static final AccountRepository accountRepository = AccountRepositoryImplMock
      .getInstance();

  @Override
  public List<Account> getAccounts() {
    return accountRepository.getAccounts(1, 20);
  }

  @Override
  public List<Account> getAccounts(int page, int count) {
    return accountRepository.getAccounts(page, count);
  }

  @Override
  public Account getByName(String name) {
    return accountRepository.getAccountByName(name);
  }

  @Override
  public Account getById(Long id) {
    return accountRepository.getAccountById(id);
  }

  @Override
  public Account getAccountByLogin(String login) {
    return accountRepository.getAccountByLogin(login);
  }

  @Override
  public void insert(Account account) {
    accountRepository.addAccount(account);
  }
}
