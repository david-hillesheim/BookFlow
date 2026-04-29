package com.bookflow;

import com.bookflow.dto.response.LoanResponse;
import com.bookflow.model.Book;
import com.bookflow.model.Loan;
import com.bookflow.model.Member;
import com.bookflow.model.enums.LoanStatus;
import com.bookflow.repository.BookRepository;
import com.bookflow.repository.LoanRepository;
import com.bookflow.repository.MemberRepository;
import com.bookflow.service.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void mustCalculateCorrectlyFineWhenLate() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setAvailableCopies(1);

        Loan loan = new Loan();
        loan.setId(1L);
        loan.setExpectedReturnDate(LocalDate.now().minusDays(5));
        loan.setBook(book);

        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        Mockito.when(loanRepository.save(any(Loan.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        LoanResponse loanWithFine = loanService.returnLoan(loan.getId());

        // Assert
        assertEquals(new BigDecimal("10.00"), loanWithFine.fine());
    }

    @Test
    public void mustReturnOverdueLoans() {
        // Arrange
        List<Loan> mockList = new ArrayList<>();
        Loan loan = new Loan(
                1L,
                new Book(),
                new Member(),
                LocalDate.now(),
                LocalDate.now().minusDays(3),
                null,
                LoanStatus.ACTIVE,
                BigDecimal.ZERO
        );
        mockList.add(loan);
        Mockito.when(loanRepository.findByLoanStatusAndExpectedReturnDateBefore(
                any(), any())).thenReturn(mockList);

        // Act
        List<Loan> result = loanService.findOverdueLoans();

        // Assert
        assertEquals(1, result.size());
        assertEquals(LoanStatus.ACTIVE, result.get(0).getLoanStatus());
    }
}
