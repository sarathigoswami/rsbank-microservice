package com.rsbank.loan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rsbank.loan.constants.CustomStatus;
import com.rsbank.loan.dto.ErrorResponseDto;
import com.rsbank.loan.dto.LoanContactInfoDto;
import com.rsbank.loan.dto.LoanDto;
import com.rsbank.loan.dto.ResponseDto;
import com.rsbank.loan.service.ILoanService;

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
public class LoansController {

        private final ILoanService iLoanService;
        private final Environment environment;
        private final LoanContactInfoDto loanContactInfoDto;

    private static final Logger logger = LoggerFactory.getLogger(LoansController.class);

        @Value("${build.version}")
        private String buildVersion;

        @Operation(summary = "Create Loan REST API", description = "REST API to create new loan inside EazyBank")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PostMapping("/create")
        public ResponseEntity<ResponseDto> createLoan(
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                iLoanService.createLoan(mobileNumber);
                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(ResponseDto.build(CustomStatus.LOAN_CREATED.message()));
        }

        @Operation(summary = "Fetch Loan Details REST API", description = "REST API to fetch loan details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping("/fetch")
        public ResponseEntity<LoanDto> fetchLoanDetails(
                        @RequestHeader("rsbank-correlation-id") String correlationId,
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {

                logger.debug("rsbank-correlation-id found in LoansController : {}", correlationId);
                LoanDto loanDto = iLoanService.fetchLoan(mobileNumber);
                return ResponseEntity.status(HttpStatus.OK).body(loanDto);
        }

        @Operation(summary = "Update Loan Details REST API", description = "REST API to update loan details based on a loan number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PutMapping("/update")
        public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto loanDto) {
                boolean isUpdated = iLoanService.updateLoan(loanDto);
                if (isUpdated) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(ResponseDto.build(CustomStatus.LOAN_UPDATED.message()));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(ResponseDto.build(CustomStatus.SOMETHING_WENT_WRONG.message()));
                }
        }

        @Operation(summary = "Delete Loan Details REST API", description = "REST API to delete Loan details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                        @ApiResponse(responseCode = "417", description = "Expectation Failed"),
                        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @DeleteMapping("/delete")
        public ResponseEntity<ResponseDto> deleteLoanDetails(
                        @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
                boolean isDeleted = iLoanService.deleteLoan(mobileNumber);
                if (isDeleted) {
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(ResponseDto.build(CustomStatus.LOAN_DELETED.message()));
                } else {
                        return ResponseEntity
                                        .status(HttpStatus.EXPECTATION_FAILED)
                                        .body(ResponseDto.build(CustomStatus.SOMETHING_WENT_WRONG.message()));
                }
        }

        @Operation(summary = "Get Build information", description = "Get Build information that is deployed into cards microservice")
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

        @Operation(summary = "Get Java version", description = "Get Java versions details that is installed into cards microservice")
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
        public ResponseEntity<LoanContactInfoDto> getContactInfo() {
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(loanContactInfoDto);
        }

}
