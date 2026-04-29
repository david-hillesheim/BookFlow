package com.bookflow.service;

import com.bookflow.exception.BusinessException;
import com.bookflow.exception.ResourceNotFoundException;
import com.bookflow.model.Book;
import com.bookflow.model.Loan;
import com.bookflow.model.Member;
import com.bookflow.model.enums.LoanStatus;
import com.bookflow.repository.BookRepository;
import com.bookflow.repository.LoanRepository;
import com.bookflow.repository.MemberRepository;
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
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Loan registerLoan(Loan loan) {
        Book book = bookRepository.findById(loan.getBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum livro encontrado para o id:" + loan.getBook().getId()));
        Member member = memberRepository.findById(loan.getMember().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum membro encontrado para o id: " + loan.getMember().getId()));

        if (book.getAvailableCopies() <= 0){
            throw new BusinessException("O livro não possui nenhuma cópia disponível!");
        }

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
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado para o id: " + id));

        if (existingLoan.getLoanStatus() == LoanStatus.RETURNED) {
            throw new BusinessException("Empréstimo já devolvido!");
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
        Book loanBook = bookRepository.findById(existingLoan.getBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum livro encontrado para o id:" + existingLoan.getBook().getId()));
        loanBook.setAvailableCopies(loanBook.getAvailableCopies() + 1);

        return loanRepository.save(existingLoan);
    }

    public List<Loan> findOverdueLoans() {
        return loanRepository.findByLoanStatusAndExpectedReturnDateBefore(
                LoanStatus.ACTIVE,
                LocalDate.now()
        );
    }

    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoanMemberHistory(Long id) {
        return loanRepository.findByMemberId(id);
    }
}
