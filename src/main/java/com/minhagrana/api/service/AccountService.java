package com.minhagrana.api.service;

import com.minhagrana.api.dao.AccountDAO;
import com.minhagrana.api.domain.Account;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class AccountService {

    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Optional<Account> findById(@NotNull UUID accountId) {
        return accountDAO.findById(accountId);
    }

    public Optional<Account> save(@NotNull Account user) {
        return accountDAO.save(user);
    }
}
