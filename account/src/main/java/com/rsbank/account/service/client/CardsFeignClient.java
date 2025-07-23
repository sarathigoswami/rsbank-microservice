package com.rsbank.account.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.rsbank.account.dto.CardDto;

@FeignClient(name = "cards")
public interface CardsFeignClient {
    
    @GetMapping(value = "api/fetch", consumes = "application/json")
    public ResponseEntity<CardDto> fetchCardDetails(@RequestHeader("rsbank-correlation-id") String correlationId, @RequestParam String mobileNumber);

}
