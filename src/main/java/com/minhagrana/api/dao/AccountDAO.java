package com.minhagrana.api.dao;

import com.minhagrana.api.dao.mapper.AccountRowMapper;
import com.minhagrana.api.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Component
@Validated
public class AccountDAO {
    private static final Logger log = LoggerFactory.getLogger(AccountDAO.class);

    private static final AccountRowMapper ACCOUNT_ROW_MAPPER = new AccountRowMapper();
    private static final String FIND_BY_ID = "SELECT ID, CREATED_DATE, USER_ID, BANK_ID, AGENCY_NUMBER, ACCOUNT_NUMBER FROM ACCOUNTS";
    private static final String SAVE = "INSERT INTO ACCOUNTS (ID, CREATED_DATE, USER_ID, BANK_ID, AGENCY_NUMBER, ACCOUNT_NUMBER) " +
            "VALUES (:id, :created_date, :user_id, :bank_id, :agency_number, :account_number)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AccountDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional(readOnly = true)
    public Optional<Account> findById(@NotNull UUID userId) {
        return namedParameterJdbcTemplate.query(
                FIND_BY_ID, Collections.singletonMap("id", userId.toString()), ACCOUNT_ROW_MAPPER)
                .stream().findFirst();
    }

    @Transactional
    public Optional<Account> save(@NotNull Account account) {
        UUID accountId = UUID.randomUUID();
        Instant createdDate = Instant.now();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", accountId);
        params.addValue("created_date", createdDate.atOffset(ZoneOffset.UTC).toLocalDateTime());
        params.addValue("user_id", account.getUserId());
        params.addValue("bank_id", account.getBankId());
        params.addValue("agency_number", account.getAgencyNumber());
        params.addValue("account_number", account.getAccountNumber());

        int afftectedRows = namedParameterJdbcTemplate.update(SAVE, params);
        log.info("Account {} saved", accountId);

        return afftectedRows > 0 ?
                Optional.of(new Account(accountId, createdDate, account.getUserId(), account.getBankId(),
                        account.getAgencyNumber(), account.getAccountNumber())) :
                Optional.empty();
    }
}
