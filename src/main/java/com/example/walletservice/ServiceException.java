package com.example.walletservice;

public class ServiceException extends Exception{
    private int statusCode = 500;

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return statusCode;
    }

}
