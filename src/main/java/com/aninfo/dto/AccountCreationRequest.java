package com.aninfo.dto;

public class AccountCreationRequest {
    public Double balance;

    public AccountCreationRequest() {
    }

    public AccountCreationRequest(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
