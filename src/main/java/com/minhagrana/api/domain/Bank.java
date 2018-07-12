package com.minhagrana.api.domain;

import java.util.UUID;

public class Bank {
    private final UUID id;
    private final String code;
    private final String name;

    public Bank(UUID id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
