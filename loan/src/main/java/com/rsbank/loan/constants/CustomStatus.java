package com.rsbank.loan.constants;

public enum CustomStatus {

    //Success Statuses
    LOAN_CREATED(200),
    LOAN_UPDATED(201),
    LOAN_DELETED(202),

    //Client Error Statuses
    RESOURCE_NOT_FOUND(400),
    LOAN_EXISTS_WITH_MOBILE(401),

    //Server Error Statuses
    SOMETHING_WENT_WRONG(500);

    //Unexpected Error Statuses

    int code;

    private CustomStatus(int code) {
        this.code = code;
    }

    public String message() {
        return switch (this) {
            case LOAN_CREATED -> String.format("%s|Loan created", LOAN_CREATED.code);
            case LOAN_UPDATED -> String.format("%s|Loan updated", LOAN_UPDATED.code);
            case LOAN_DELETED -> String.format("%s|Loan deleted", LOAN_DELETED.code);
            case SOMETHING_WENT_WRONG -> String.format("%s|Something went wrong, try again or contact dev team", SOMETHING_WENT_WRONG.code);
            default -> "ERROR";
        };
    }

    public String message(String var1) {
        return switch (this) {
            case LOAN_EXISTS_WITH_MOBILE -> String.format("%s|Loan already exists with mobile %s", LOAN_EXISTS_WITH_MOBILE.code, var1);
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
