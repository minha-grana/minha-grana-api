package com.minhagrana.api.dao;

import com.minhagrana.api.dao.mapper.UserRowMapper;
import com.minhagrana.api.domain.User;
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
public class UserDAO {
    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String FIND_BY_ID = "SELECT ID, CREATED_DATE, NAME, EMAIL FROM USERS";
    private static final String SAVE = "INSERT INTO USERS (ID, CREATED_DATE, NAME, EMAIL) VALUES (:id, :created_date, :name, :email)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(@NotNull UUID userId) {
        return namedParameterJdbcTemplate.query(
                FIND_BY_ID, Collections.singletonMap("id", userId.toString()), USER_ROW_MAPPER)
                .stream().findFirst();
    }

    @Transactional
    public Optional<User> save(@NotNull User user) {
        UUID userId = UUID.randomUUID();
        Instant createdDate = Instant.now();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", userId);
        params.addValue("created_date", createdDate.atOffset(ZoneOffset.UTC).toLocalDateTime());
        params.addValue("name", user.getName());
        params.addValue("email", user.getEmail());

        int afftectedRows = namedParameterJdbcTemplate.update(SAVE, params);
        log.info("User {} saved", userId);

        return afftectedRows > 0 ?
                Optional.of(new User(userId, createdDate, user.getName(), user.getEmail())) :
                Optional.empty();
    }
}
