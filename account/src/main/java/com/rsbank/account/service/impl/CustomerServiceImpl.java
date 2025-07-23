package com.rsbank.account.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rsbank.account.constants.CustomStatus;
import com.rsbank.account.dto.AccountsDto;
import com.rsbank.account.dto.CardDto;
import com.rsbank.account.dto.CustomerDetailsDto;
import com.rsbank.account.dto.LoanDto;
import com.rsbank.account.entity.Accounts;
import com.rsbank.account.entity.Customer;
import com.rsbank.account.exception.ResourceNotFoundException;
import com.rsbank.account.mapper.AccountsMapper;
import com.rsbank.account.mapper.CustomerMapper;
import com.rsbank.account.repository.AccountsRepository;
import com.rsbank.account.repository.CustomerRepository;
import com.rsbank.account.service.ICustomerService;
import com.rsbank.account.service.client.CardsFeignClient;
import com.rsbank.account.service.client.LoansFeignClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(
            () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Customer", "mobileNumber", mobileNumber))
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
        .orElseThrow(
            () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Accounts", "customerId", customer.getCustomerId().toString()))
        );
        
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoanDto(loanDtoResponseEntity.getBody());

        ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardDto(cardDtoResponseEntity.getBody());
        
        return customerDetailsDto;
        
    }

}
