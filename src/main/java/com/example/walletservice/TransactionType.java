package com.example.walletservice;

public enum TransactionType {

    DEBIT(1),
    CREDIT(2);

    private final int value;

    TransactionType(final int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}
