package com.rsbank.account.constants;

public enum CustomStatus {

    //Success Statuses
    ACCOUNT_CREATED(200),
    ACCOUNT_UPDATED(201),
    ACCOUNT_DELETED(202),

    //Client Error Statuses
    RESOURCE_NOT_FOUND(400),
    CUSTOMER_EXISTS_WITH_MOBILE(401),

    //Server Error Statuses
    SOMETHING_WENT_WRONG(500);

    //Unexpected Error Statuses

    int code;

    private CustomStatus(int code) {
        this.code = code;
    }

    public String message() {
        return switch (this) {
            case ACCOUNT_CREATED -> String.format("%s|Account created", ACCOUNT_CREATED.code);
            case ACCOUNT_UPDATED -> String.format("%s|Account updated", ACCOUNT_UPDATED.code);
            case ACCOUNT_DELETED -> String.format("%s|Account deleted", ACCOUNT_DELETED.code);
            case SOMETHING_WENT_WRONG -> String.format("%s|Something went wrong, try again or contact dev team", SOMETHING_WENT_WRONG.code);
            default -> "ERROR";
        };
    }

    public String message(String var1) {
        return switch (this) {
            case CUSTOMER_EXISTS_WITH_MOBILE -> String.format("%s|Customer already exists with mobile %s", CUSTOMER_EXISTS_WITH_MOBILE.code, var1);
            default -> "ERROR";
        };
    }

    public String message(String var1, String var2, String var3) {
        return switch (this) {
            case RESOURCE_NOT_FOUND -> String.format("%s|%s not found with %s : %s", RESOURCE_NOT_FOUND.code, var1, var2, var3);
            default -> "ERROR";
        };
    }

}
