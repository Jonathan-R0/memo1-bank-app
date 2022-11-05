package com.aninfo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cbu;

    private Double balance;

    private Double promoCreditLeft;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cbu_account")
    private List<Transaction> transactionList;

    public Account(){
    }

    public Account(Double balance) {
        this.balance = balance;
        this.transactionList = new ArrayList<>();
        this.promoCreditLeft = 0d;
    }

    public Account(Double balance, Double promoCreditLeft) {
        this.balance = balance;
        this.transactionList = new ArrayList<>();
        this.promoCreditLeft = promoCreditLeft;
    }

    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public void decPromoAccumulated(Double amount) {
        this.promoCreditLeft += amount;
    }

    public Double getPromoCreditLeft() {
        return this.promoCreditLeft;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionList() {
        return this.transactionList;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionList.add(transaction);
    }

}
