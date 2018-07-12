package com.minhagrana.api.dao.mapper;

import com.minhagrana.api.domain.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Account(
                UUID.fromString(resultSet.getString("id")),
                Optional.of(resultSet.getTimestamp("created_date")).map(Timestamp::toInstant).orElse(null),
                UUID.fromString(resultSet.getString("user_id")),
                UUID.fromString(resultSet.getString("bank_id")),
                resultSet.getString("agency_number"),
                resultSet.getString("account_number"));
    }

}
