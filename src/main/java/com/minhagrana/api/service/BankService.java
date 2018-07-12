package com.minhagrana.api.service;

import com.minhagrana.api.dao.BankDAO;
import com.minhagrana.api.domain.Bank;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class BankService {

    private final BankDAO bankDAO;

    public BankService(BankDAO bankDAO) {
        this.bankDAO = bankDAO;
    }

    public Optional<Bank> findById(@NotNull UUID bankId) {
        return bankDAO.findById(bankId);
    }
}
