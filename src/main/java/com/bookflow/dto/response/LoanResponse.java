package com.bookflow.dto.response;

import com.bookflow.model.enums.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponse (Long id, Long bookId, String bookTitle, Long memberId, String memberName, LocalDate loanDate, LocalDate expectedReturnDate, LocalDate actualReturnDate, LoanStatus loanStatus, BigDecimal fine) {}