package com.minhagrana.api.dao.mapper;

import com.minhagrana.api.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(
                UUID.fromString(resultSet.getString("id")),
                Optional.of(resultSet.getTimestamp("created_date")).map(Timestamp::toInstant).orElse(null),
                resultSet.getString("name"),
                resultSet.getString("email"));
    }

}
