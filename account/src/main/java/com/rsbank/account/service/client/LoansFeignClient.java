package com.rsbank.account.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.rsbank.account.dto.LoanDto;

@FeignClient(name = "loans")
public interface LoansFeignClient {
    
    @GetMapping(value = "api/fetch", consumes = "application/json")
    public ResponseEntity<LoanDto> fetchLoanDetails(@RequestHeader("rsbank-correlation-id") String correlationId, @RequestParam String mobileNumber);

}
