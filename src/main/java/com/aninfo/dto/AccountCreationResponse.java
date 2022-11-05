package com.aninfo.dto;

import com.aninfo.model.Account;

public class AccountCreationResponse {
    private Long cbu;
    private Double balance;

    public AccountCreationResponse(Account account) {
        this.cbu = account.getCbu();
        this.balance = account.getBalance();
    }
}
