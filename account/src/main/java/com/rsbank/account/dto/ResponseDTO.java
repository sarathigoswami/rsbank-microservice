package com.rsbank.account.dto;

import com.rsbank.account.constants.AccountsStatus;

import lombok.Data;

@Data
public class ResponseDTO {

    private int status;
    private String message;

    public ResponseDTO(AccountsStatus status) {
        this.status = status.code();
        this.message = status.message();
    }

}
