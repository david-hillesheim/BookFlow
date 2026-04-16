package com.bookflow.service;

import com.bookflow.model.Book;
import com.bookflow.model.Loan;
import com.bookflow.model.Member;
import com.bookflow.model.enums.LoanStatus;
import com.bookflow.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final MemberService memberService;

    @Transactional
    public Loan registerLoan(Loan loan) {

        Book book = bookService.findBookById(loan.getBook().getId());
        Member member = memberService.findMemberById(loan.getMember().getId());

        if (book.getAvailableCopies() <= 0){
            throw new RuntimeException("O livro não possui nenhuma cópia disponível!");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookService.updateBook(book.getId(), book);

        loan.setLoanDate(LocalDate.now());
        loan.setExpectedReturnDate(loan.getLoanDate().plusDays(14));
        loan.setLoanStatus(LoanStatus.ACTIVE);
        loan.setFine(BigDecimal.ZERO);

        return loanRepository.save(loan);
    }

}
