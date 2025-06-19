package com.rsbank.account.service;

import com.rsbank.account.dto.CustomerDto;

public interface IAccountsService {

    public void createAccount(CustomerDto customerDto);

    public CustomerDto fetchAccount(String mobileNumber);
    
    public boolean updateAccount(CustomerDto customerDto);

    public boolean deleteAccount(String mobileNumber);

}
