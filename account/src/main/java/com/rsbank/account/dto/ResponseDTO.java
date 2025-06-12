package com.rsbank.account.dto;

import com.rsbank.account.constants.AccountsStatus;

import lombok.Data;

@Data
public class ResponseDto {

    private int status;
    private String message;

    public ResponseDto(AccountsStatus status) {
        this.status = status.code();
        this.message = status.message();
    }

}
