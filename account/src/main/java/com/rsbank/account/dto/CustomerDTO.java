package com.rsbank.account.dto;

import com.rsbank.account.constants.Literals;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = Literals.NAL_BLANK)
    @Size(min = 3, max = 50)
    private String name;

    @NotEmpty(message = Literals.NAL_BLANK)
    @Email(message = Literals.INV_FORMAT)
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = Literals.INV_FORMAT)
    private String mobileNumber;

    private AccountsDto accountsDto;

}
