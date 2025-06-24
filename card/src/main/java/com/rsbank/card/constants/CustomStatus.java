package com.rsbank.card.constants;

public enum CustomStatus {

    //Success Statuses
    CARD_CREATED(200),
    CARD_UPDATED(201),
    CARD_DELETED(202),

    //Client Error Statuses
    RESOURCE_NOT_FOUND(400),
    CARD_EXISTS_WITH_MOBILE(401),

    //Server Error Statuses
    SOMETHING_WENT_WRONG(500);

    //Unexpected Error Statuses

    int code;

    private CustomStatus(int code) {
        this.code = code;
    }

    public String message() {
        return switch (this) {
            case CARD_CREATED -> String.format("%s|Card created", CARD_CREATED.code);
            case CARD_UPDATED -> String.format("%s|Card updated", CARD_UPDATED.code);
            case CARD_DELETED -> String.format("%s|Card deleted", CARD_DELETED.code);
            case SOMETHING_WENT_WRONG -> String.format("%s|Something went wrong, try again or contact dev team", SOMETHING_WENT_WRONG.code);
            default -> "ERROR";
        };
    }

    public String message(String var1) {
        return switch (this) {
            case CARD_EXISTS_WITH_MOBILE -> String.format("%s|Card already exists with mobile %s", CARD_EXISTS_WITH_MOBILE.code, var1);
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
