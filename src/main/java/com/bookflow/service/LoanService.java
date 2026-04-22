package com.bookflow.service;

import com.bookflow.model.Book;
import com.bookflow.model.Loan;
import com.bookflow.model.Member;
import com.bookflow.model.enums.LoanStatus;
import com.bookflow.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
        // There is no need to verify if the member or book exists, as this is already done in the service.
        if (book.getAvailableCopies() <= 0){
            throw new RuntimeException("O livro não possui nenhuma cópia disponível!");
        }

        // There is no need to update the book because of the Transactional Annotation
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        loan.setBook(book);
        loan.setMember(member);
        loan.setLoanDate(LocalDate.now());
        loan.setExpectedReturnDate(loan.getLoanDate().plusDays(14));
        loan.setLoanStatus(LoanStatus.ACTIVE);
        loan.setFine(BigDecimal.ZERO);

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan returnLoan(Long id){

        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado para o id: " + id));

        if (existingLoan.getLoanStatus() == LoanStatus.RETURNED) {
            throw new RuntimeException("Empréstimo já devolvido!");
        }

        existingLoan.setActualReturnDate(LocalDate.now());
        if (existingLoan.getActualReturnDate().isAfter(existingLoan.getExpectedReturnDate())) {

            Long daysLate = ChronoUnit.DAYS.between(existingLoan.getExpectedReturnDate(), existingLoan.getActualReturnDate());
            BigDecimal daysLateBigDecimal = BigDecimal.valueOf(daysLate);
            BigDecimal dailyValue = new BigDecimal("2.00");
            BigDecimal fine = daysLateBigDecimal.multiply(dailyValue);
            existingLoan.setFine(fine);

        }

        existingLoan.setLoanStatus(LoanStatus.RETURNED);
        Book loanBook = bookService.findBookById(existingLoan.getBook().getId());
        loanBook.setAvailableCopies(loanBook.getAvailableCopies() + 1);

        return loanRepository.save(existingLoan);
    }

    public List<Loan> findOverdueLoans() {
        return loanRepository.findByLoanStatusAndExpectedReturnDateBefore(
                LoanStatus.ACTIVE,
                LocalDate.now()
        );
    }

    public List<Loan> getLoanMemberHistory(Long id) {
        return loanRepository.findByMemberId(id);
    }
}
