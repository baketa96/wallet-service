package com.example.walletservice.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "player_account")
public class PlayerAccount {

    @Id
    private Long id;
    private String playerName;
    private String playerSurname;
    private Double balance;

    public PlayerAccount() {
    }

    public PlayerAccount(Long id, String playerName, String playerSurname, Double balance) {
        this.id = id;
        this.playerName = playerName;
        this.playerSurname = playerSurname;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerSurname() {
        return playerSurname;
    }

    public void setPlayerSurname(String playerSurname) {
        this.playerSurname = playerSurname;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
