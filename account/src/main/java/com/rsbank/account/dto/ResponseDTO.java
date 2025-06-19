package com.rsbank.account.dto;

import lombok.Data;

@Data
public class ResponseDto {

    private int status;
    private String message;

    private ResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseDto build(String statusMessage) {
        String[] statusParts = statusMessage.split("\\|"); // Split
        int status = Integer.parseInt(statusParts[0]);
        String message = statusParts[1];

        return new ResponseDto(status, message);
    }

}
