package com.rsbank.loan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsbank.loan.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByMobileNumber(String mobileNumber);

    Optional<Loan> findByLoanNumber(String loanNumber);

}
