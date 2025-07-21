package com.rsbank.account.service;

import com.rsbank.account.dto.CustomerDetailsDto;

public interface ICustomerService {

    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
