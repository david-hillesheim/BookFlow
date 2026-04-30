package com.bookflow.repository;

import com.bookflow.dto.response.LoanResponse;
import com.bookflow.model.Loan;
import com.bookflow.model.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByLoanStatusAndExpectedReturnDateBefore(LoanStatus status, LocalDate date);

    List<Loan> findLoansByMemberId(Long memberId);
}
