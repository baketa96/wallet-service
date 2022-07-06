package com.example.walletservice.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private PlayerAccount playerAccount;
    private Double amount;
    private Date date;
    private int transactionType;

    public Transaction() {
    }

    public Transaction(Long id, PlayerAccount playerAccount, Double amount, Date date, int transactionType) {
        this.id = id;
        this.playerAccount = playerAccount;
        this.amount = amount;
        this.date = date;
        this.transactionType = transactionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerAccount getPlayerAccount() {
        return playerAccount;
    }

    public void setPlayerAccount(PlayerAccount playerAccount) {
        this.playerAccount = playerAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }
}
