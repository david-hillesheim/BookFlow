package com.bookflow.dto.response;

import com.bookflow.model.enums.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponse (Long id, Long bookId, Long memberId, LocalDate loanDate, LocalDate expectedReturnDate, LocalDate actualReturnDate, LoanStatus loanStatus, BigDecimal fine) {}