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
    List<LoanResponse> findByLoanStatusAndExpectedReturnDateBefore(LoanStatus status, LocalDate date);

    // Returns all Loans associated with member ID
    List<LoanResponse> findByMemberId(Long memberId);
}
