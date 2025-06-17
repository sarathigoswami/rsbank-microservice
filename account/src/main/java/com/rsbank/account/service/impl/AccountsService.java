package com.rsbank.account.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.rsbank.account.constants.Literals;
import com.rsbank.account.dto.CustomerDto;
import com.rsbank.account.entity.Accounts;
import com.rsbank.account.entity.Customer;
import com.rsbank.account.exception.CustomerExistsException;
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
            throw new CustomerExistsException("Customer already exists with mobile number " + customer.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
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

}
