package com.minhagrana.api.dao;

import com.minhagrana.api.dao.mapper.BankRowMapper;
import com.minhagrana.api.domain.Bank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Component
@Validated
public class BankDAO {
    private static final Logger log = LoggerFactory.getLogger(BankDAO.class);

    private static final BankRowMapper BANK_ROW_MAPPER = new BankRowMapper();
    private static final String FIND_BY_ID = "SELECT ID, CODE, NAME FROM BANKS";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BankDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional(readOnly = true)
    public Optional<Bank> findById(@NotNull UUID bankId) {
        return namedParameterJdbcTemplate.query(
                FIND_BY_ID, Collections.singletonMap("id", bankId.toString()), BANK_ROW_MAPPER)
                .stream().findFirst();
    }
}
