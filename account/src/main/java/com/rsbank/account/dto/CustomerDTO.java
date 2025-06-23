package com.rsbank.account.dto;

import com.rsbank.account.constants.Literals;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer", example = "Eazy Bytes"
    )
    @NotEmpty(message = Literals.NAL_BLANK)
    @Size(min = 3, max = 50)
    private String name;

    @Schema(
            description = "Email address of the customer", example = "tutor@eazybytes.com"
    )
    @NotEmpty(message = Literals.NAL_BLANK)
    @Email(message = Literals.INV_FORMAT)
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @NotEmpty(message = Literals.NAL_BLANK)
    @Pattern(regexp = "^[0-9]{10}$", message = Literals.INV_FORMAT)
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    @NotEmpty(message = Literals.NAL_BLANK)
    private AccountsDto accountsDto;

}
