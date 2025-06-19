package com.rsbank.account.dto;

import com.rsbank.account.constants.Literals;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = Literals.NAL_BLANK)
    @Pattern(regexp = "^[0-9]{10}$", message = Literals.INV_FORMAT)
    private Long accountNumber;

    @NotEmpty(message = Literals.NAL_BLANK)
    private String accountType;
    
    @NotEmpty(message = Literals.NAL_BLANK)
    private String branchAddress;

}
