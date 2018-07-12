package com.minhagrana.api.dao.mapper;

import com.minhagrana.api.domain.Bank;
import com.minhagrana.api.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BankRowMapper implements RowMapper<Bank> {

    @Override
    public Bank mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Bank(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("code"),
                resultSet.getString("name"));
    }

}
