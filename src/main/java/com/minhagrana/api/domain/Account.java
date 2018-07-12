package com.minhagrana.api.domain;

import java.time.Instant;
import java.util.UUID;

public class Account {
    private final UUID id;
    private final Instant createdDate;
    private final UUID userId;
    private final UUID bankId;
    private final String agencyNumber;
    private final String accountNumber;

    public Account(UUID id, Instant createdDate, UUID userId, UUID bankId, String agencyNumber, String accountNumber) {
        this.id = id;
        this.createdDate = createdDate;
        this.userId = userId;
        this.bankId = bankId;
        this.agencyNumber = agencyNumber;
        this.accountNumber = accountNumber;
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getBankId() {
        return bankId;
    }

    public String getAgencyNumber() {
        return agencyNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

}
