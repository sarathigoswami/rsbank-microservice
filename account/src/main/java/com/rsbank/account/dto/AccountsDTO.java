package com.rsbank.account.dto;

import com.rsbank.account.constants.Literals;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @NotEmpty(message = Literals.NAL_BLANK)
    @Pattern(regexp = "^[0-9]{10}$", message = Literals.INV_FORMAT)
    @Schema(
            description = "Account Number of RS Bank account", example = "3454433243"
    )
    private Long accountNumber;

    @NotEmpty(message = Literals.NAL_BLANK)
    @Schema(
            description = "Account type of RS Bank account", example = "SA (for Savings)"
    )
    private String accountType;
    
    @NotEmpty(message = Literals.NAL_BLANK)
    @Schema(
            description = "RS Bank branch address", example = "123 NewYork"
    )
    private String branchAddress;

}
