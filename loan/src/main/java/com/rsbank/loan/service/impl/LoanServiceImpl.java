package com.rsbank.loan.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.rsbank.loan.constants.CustomStatus;
import com.rsbank.loan.constants.Literals;
import com.rsbank.loan.dto.LoanDto;
import com.rsbank.loan.entity.Loan;
import com.rsbank.loan.exception.LoanAlreadyExistsException;
import com.rsbank.loan.exception.ResourceNotFoundException;
import com.rsbank.loan.mapper.LoanMapper;
import com.rsbank.loan.repository.LoanRepository;
import com.rsbank.loan.service.ILoanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements ILoanService {

    private final LoanRepository loanRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoan= loanRepository.findByMobileNumber(mobileNumber);
        if(optionalLoan.isPresent()){
            throw new LoanAlreadyExistsException(CustomStatus.LOAN_EXISTS_WITH_MOBILE.message(mobileNumber));
        }
        loanRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(Literals.HOME_LOAN);
        newLoan.setTotalLoan(Literals.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(Literals.NEW_LOAN_LIMIT);
        return newLoan;
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Loan", "mobileNumber", mobileNumber))
        );
        return LoanMapper.mapToLoanDto(loan, new LoanDto());
    }

    /**
     *
     * @param loanDto - LoanDto Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public boolean updateLoan(LoanDto loanDto) {
        Loan loan = loanRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Loan", "LoanNumber", loanDto.getLoanNumber())));
        LoanMapper.mapToLoan(loanDto, loan);
        loanRepository.save(loan);
        return  true;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of loan details is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(CustomStatus.RESOURCE_NOT_FOUND.message("Loan", "mobileNumber", mobileNumber))
        );
        loanRepository.deleteById(loan.getLoanId());
        return true;
    }
    
}
