package com.rsbank.account.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {

    private String apiPath;
    private HttpStatus httpStatus;
    private String errorMessage;
    private Date timestamp;

}
