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

    public Double getSum() {
        return this.sum;
    }
    public Long getId() {
        return this.id;
    }


    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, Double sum) {
        this.transactionType= transactionType;
        this.sum = sum;
    }
}
