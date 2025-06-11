package com.rsbank.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsbank.account.constants.AccountsStatus;
import com.rsbank.account.dto.CustomerDTO;
import com.rsbank.account.dto.ResponseDTO;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AccountsController {

    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customer) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsStatus.CREATED));
    }

}
