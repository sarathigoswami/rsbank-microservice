package com.rsbank.account.exception;

public class CustomerExistsException extends RuntimeException {

    public CustomerExistsException(String message) {
        super(message);
    }
}
