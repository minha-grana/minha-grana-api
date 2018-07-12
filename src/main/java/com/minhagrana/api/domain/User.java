package com.minhagrana.api.domain;

import java.time.Instant;
import java.util.UUID;

public class User {
    private final UUID id;
    private final Instant createdDate;
    private final String name;
    private final String email;

    public User(UUID id, Instant createdDate, String name, String email) {
        this.id = id;
        this.createdDate = createdDate;
        this.name = name;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
