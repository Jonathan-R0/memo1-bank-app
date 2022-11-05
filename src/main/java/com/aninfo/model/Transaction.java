package com.aninfo.model;


import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Double sum;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, Double sum) {
        this.transactionType= transactionType;
        this.sum = sum;
    }
}
