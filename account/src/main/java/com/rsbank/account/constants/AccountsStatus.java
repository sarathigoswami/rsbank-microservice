package com.rsbank.account.constants;

public enum AccountsStatus {

    CREATED(201, "Account Created");

    int code;
    String message;

    private AccountsStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
