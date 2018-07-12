package com.minhagrana.api.service;

import com.minhagrana.api.dao.UserDAO;
import com.minhagrana.api.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> findById(@NotNull UUID userId) {
        return userDAO.findById(userId);
    }

    public Optional<User> save(@NotNull User user) {
        return userDAO.save(user);
    }
}
