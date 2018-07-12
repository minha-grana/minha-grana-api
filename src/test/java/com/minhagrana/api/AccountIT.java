package com.minhagrana.api;

import com.minhagrana.api.configuration.IntegrationTest;
import com.minhagrana.api.domain.Account;
import com.minhagrana.api.domain.User;
import com.minhagrana.api.service.AccountService;
import com.minhagrana.api.service.BankService;
import com.minhagrana.api.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertTrue;

public class AccountIT extends IntegrationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private UUID bankId;
    private UUID userId;

    @Before
    public void setUp() {
        bankId = UUID.randomUUID();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", bankId);
        params.addValue("code", "999");
        params.addValue("name", "bank-test");
        namedParameterJdbcTemplate.update("INSERT INTO BANKS (ID, CODE, NAME) VALUES (:id, :code, :name)", params);
        assertTrue(bankService.findById(bankId).isPresent());

        userService.save(new User(null, null, "User test", "usertest@minhagrana.com.br"))
                .ifPresent(user -> userId = user.getId());
        assertTrue(userService.findById(userId).isPresent());
    }

    @Test
    public void addAccount() {
        AtomicReference<UUID> atomicAccountId = new AtomicReference<>();
        accountService.save(new Account(null, null, userId, bankId, "0000", "00000000"))
                .ifPresent(user -> atomicAccountId.set(user.getId()));
        UUID accountId = atomicAccountId.get();
        assertTrue(accountService.findById(accountId).isPresent());
    }
}
