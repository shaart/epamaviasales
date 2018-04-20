package com.epam.aviasales.repositories.implMock;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Role;
import com.epam.aviasales.repositories.AccountRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

public class AccountRepositoryImplMock implements AccountRepository {

  private static volatile AccountRepository instance;
  private static final Map<Long, Account> ACCOUNTS_CACHE = new HashMap<>();

  public static AccountRepository getInstance() {
    AccountRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (AccountRepositoryImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AccountRepositoryImplMock();
        }
      }
    }

    return localInstance;
  }

  private AccountRepositoryImplMock() {
    final int CACHE_COUNT = 100;

    for (int i = 1; i < CACHE_COUNT; i++) {
      ACCOUNTS_CACHE.put(Long.valueOf(i),
          Account.builder().id(Long.valueOf(i))
              .role(i % 30 == 0 ? Role.ADMIN : (i % 15 == 0 ? Role.MANAGER : Role.USER))
              .name(i == 15 ? "Bob Marley" : "BOB-" + i).login("smartbob" + i)
              .password(DigestUtils.sha256Hex("SHA256-" + i))
              .email("bob" + i + "@bobworld.com")
              .phone("123456789")
              .build());
    }
  }

  @Override
  public List<Account> getAccounts() {
    return getAccounts(1, Integer.MAX_VALUE);
  }

  @Override
  public List<Account> getAccounts(int page, int count) {
    List<Account> accountList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return accountList;
    }

    final int startI = (page - 1) * count;
    for (int i = startI; i < startI + count; i++) {
      if (i >= ACCOUNTS_CACHE.size()) {
        break;
      }
      Account account = ACCOUNTS_CACHE.get(Long.valueOf(i));
      if (account != null) {
        accountList.add(account);
      }
    }
    return accountList;
  }

  @Override
  public Account getAccountById(Long id) {
    List<Account> list = new ArrayList<>();
    list.add(ACCOUNTS_CACHE.get(id));
    return list.get(0);
  }

  @Override
  public void addAccount(Account account) {
    ACCOUNTS_CACHE.put(account.getId(), account);
  }

  @Override
  public boolean isExist(String rowValue, String rowName) {
    return false;
  }

  @Override
  public List<Account> getAccountsLike(Account seekingAccount, int page, int size) {

    List<Account> accountList = new ArrayList<>();
    if (size <= 0) {
      return accountList;
    }
    if (page <= 0) {
      page = 1;
    }

    final int startI = (page - 1) * size;
    for (int i = startI; i < startI + size; i++) {
      if (i >= ACCOUNTS_CACHE.size()) {
        break;
      }
      Account account = ACCOUNTS_CACHE.get(Long.valueOf(i));
      if (account != null) {
        if ((seekingAccount.getId() == null || seekingAccount.getId().equals(account.getId()))
            && (seekingAccount.getRole() == null || seekingAccount.getRole()
            .equals(account.getRole()))
            && (seekingAccount.getName() == null || seekingAccount.getName()
            .equals(account.getName()))
            && (seekingAccount.getLogin() == null || seekingAccount.getLogin()
            .equals(account.getLogin()))
            && (seekingAccount.getEmail() == null || seekingAccount.getEmail()
            .equals(account.getEmail()))
            && (seekingAccount.getPhone() == null || seekingAccount.getPhone()
            .equals(account.getPhone()))) {
          accountList.add(account);
        }
      }
    }
    return accountList;
  }

  @Override
  public void updateAccount(Long id, Account receivedAccount) {
    ACCOUNTS_CACHE.put(id, receivedAccount);
  }

  @Override
  public void deleteAccount(Long id) {
    ACCOUNTS_CACHE.remove(id);
  }

  @Override
  public List<Account> getAccountByLogin(String login) {
    List<Account> accounts = new ArrayList<>();
    for (Account account : ACCOUNTS_CACHE.values()) {
      if (account != null && account.getLogin().equals(login)) {
        accounts.add(account);
        return accounts;
      }
    }
    return accounts;
  }

  @Override
  public void updateAccount(Account newAccount){
    for (Account account : ACCOUNTS_CACHE.values()) {
      if (account.getId().equals(newAccount.getId())) {
        account.setLogin(newAccount.getLogin());
        account.setName(newAccount.getName());
        account.setEmail(newAccount.getEmail());
        account.setLogin(newAccount.getLogin());
      }
    }
  }

  @Override
  public void updateAccountPasswordById(Long c, String password){

  }
}
