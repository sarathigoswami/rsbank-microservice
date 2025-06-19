package com.rsbank.account.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.rsbank.account.constants.CustomStatus;
import com.rsbank.account.constants.Literals;
import com.rsbank.account.dto.AccountsDto;
import com.rsbank.account.dto.CustomerDto;
import com.rsbank.account.entity.Accounts;
import com.rsbank.account.entity.Customer;
import com.rsbank.account.exception.CustomerExistsException;
import com.rsbank.account.exception.ResourceNotFoundException;
import com.rsbank.account.mapper.AccountsMapper;
import com.rsbank.account.mapper.CustomerMapper;
import com.rsbank.account.repository.AccountsRepository;
import com.rsbank.account.repository.CustomerRepository;
import com.rsbank.account.service.IAccountsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountsService implements IAccountsService {

    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (customerOptional.isPresent()) {
            throw new CustomerExistsException(CustomStatus.CUSTOMER_EXISTS_WITH_MOBILE.message(customer.getMobileNumber()));
        }
        Customer savedCustomer = customerRepository.save(customer);

        //DB Transaction
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(Literals.SA);
        newAccount.setBranchAddress(Literals.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(
            () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Customer", "mobileNumber", mobileNumber))
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
        .orElseThrow(
            () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Accounts", "customerId", customer.getCustomerId().toString()))
        );
        
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Account", "AccountNumber", accountsDto.getAccountNumber().toString()))
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Customer", "CustomerID", customerId.toString()))
            );
            CustomerMapper.mapToCustomer(customerDto,customer);

            //DB Transaction
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }
    

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Customer", "mobileNumber", mobileNumber))
        );

        //DB Transaction
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
