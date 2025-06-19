package com.rsbank.account.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rsbank.account.constants.Literals;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

    private String apiPath;
    private Integer errorCode;
    private String errorMessage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Literals.DD_MM_YYYY_HH_MM_SS, timezone = Literals.TZ_KOLKATA)
    private LocalDateTime timestamp;

}