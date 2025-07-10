package com.rsbank.account.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rsbank.account.constants.CustomStatus;
import com.rsbank.account.constants.Literals;
import com.rsbank.account.dto.AccountsContactInfoDto;
import com.rsbank.account.dto.CustomerDto;
import com.rsbank.account.dto.ErrorResponseDto;
import com.rsbank.account.dto.ResponseDto;
import com.rsbank.account.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@Tag(name = "CRUD REST APIs for Accounts in RSBank", description = "CRUD REST APIs in RSBank to CREATE, UPDATE, FETCH AND DELETE account details")
@RestController
@Validated
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
public class AccountsController {

        private final IAccountsService iAccountsService;
        private final Environment environment;
        private final AccountsContactInfoDto accountsContactInfoDto;

        @Value("${build.version}")
        private String buildVersion;

        @Operation(summary = "Create Account REST API", description = "REST API to create new Customer &  Account inside RSBank")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PostMapping("create")
        public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

                iAccountsService.createAccount(customerDto);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(ResponseDto.build(CustomStatus.ACCOUNT_CREATED.message()));
        }

        @Operation(summary = "Fetch Account Details REST API", description = "REST API to fetch Customer &  Account details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/fetch")
        public ResponseEntity<CustomerDto> fetchAccountDetails(
                        @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = Literals.INV_FORMAT) String mobileNumber) {
                CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
                return ResponseEntity.ok(customerDto);
        }

        @Operation(summary = "Update Account Details REST API", description = "REST API to update Customer &  Account details based on a account number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PutMapping("/update")
        public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
                boolean isUpdated = iAccountsService.updateAccount(customerDto);
                if (isUpdated) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(ResponseDto.build(CustomStatus.ACCOUNT_UPDATED.message()));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(ResponseDto.build(CustomStatus.SOMETHING_WENT_WRONG.message()));
                }
        }

        @Operation(summary = "Delete Account & Customer Details REST API", description = "REST API to delete Customer &  Account details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @DeleteMapping("/delete")
        public ResponseEntity<ResponseDto> deleteAccountDetails(
                        @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = Literals.INV_FORMAT) String mobileNumber) {
                boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
                if (isDeleted) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(ResponseDto.build(CustomStatus.ACCOUNT_DELETED.message()));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(ResponseDto.build(CustomStatus.SOMETHING_WENT_WRONG.message()));
                }
        }

        @Operation(summary = "Get Build information", description = "Get Build information that is deployed into accounts microservice")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/build-info")
        public ResponseEntity<String> getBuildInfo() {
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(buildVersion);
        }

        @Operation(summary = "Get Java version", description = "Get Java versions details that is installed into accounts microservice")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/java-version")
        public ResponseEntity<String> getJavaVersion() {
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(environment.getProperty("JAVA_HOME"));
        }

        @Operation(summary = "Get Contact Info", description = "Contact Info details that can be reached out in case of any issues")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/contact-info")
        public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(accountsContactInfoDto);
        }

}
