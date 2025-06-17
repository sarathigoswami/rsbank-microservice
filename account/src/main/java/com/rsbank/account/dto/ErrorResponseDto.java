package com.rsbank.account.dto;

import java.util.Date;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

    private String apiPath;
    private HttpStatusCode errorCode;
    private String errorMessage;
    private Date timestamp;

}