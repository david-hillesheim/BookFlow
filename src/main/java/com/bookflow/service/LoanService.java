package com.bookflow.service;

import com.bookflow.dto.request.LoanRequest;
import com.bookflow.dto.response.LoanResponse;
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
    public LoanResponse registerLoan(LoanRequest loanRequest) {
        Book book = bookRepository.findById(loanRequest.bookId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum livro encontrado para o id:" + loanRequest.bookId()));
        Member member = memberRepository.findById(loanRequest.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum membro encontrado para o id: " + loanRequest.memberId()));

        if (book.getAvailableCopies() <= 0){
            throw new BusinessException("O livro não possui nenhuma cópia disponível!");
        }

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setLoanDate(LocalDate.now());
        loan.setExpectedReturnDate(loan.getLoanDate().plusDays(14));
        loan.setLoanStatus(LoanStatus.ACTIVE);
        loan.setFine(BigDecimal.ZERO);

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        loan = loanRepository.save(loan);

        return new LoanResponse(
                loan.getId(),
                book.getId(),
                book.getTitle(),
                member.getId(),
                member.getName(),
                loan.getLoanDate(),
                loan.getExpectedReturnDate(),
                loan.getActualReturnDate(),
                loan.getLoanStatus(),
                loan.getFine()
        );
    }

    @Transactional
    public LoanResponse returnLoan(Long id){

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
        existingLoan.getBook().setAvailableCopies(existingLoan.getBook().getAvailableCopies() + 1);

        return new LoanResponse(
                existingLoan.getId(),
                existingLoan.getBook().getId(),
                existingLoan.getBook().getTitle(),
                existingLoan.getMember().getId(),
                existingLoan.getMember().getName(),
                existingLoan.getLoanDate(),
                existingLoan.getExpectedReturnDate(),
                existingLoan.getActualReturnDate(),
                existingLoan.getLoanStatus(),
                existingLoan.getFine()
        );
    }

    public List<LoanResponse> findOverdueLoans() {
        return loanRepository.findByLoanStatusAndExpectedReturnDateBefore(
                LoanStatus.ACTIVE,
                LocalDate.now())
                .stream()
                .map(loan -> new LoanResponse(
                        loan.getId(),
                        loan.getBook().getId(),
                        loan.getBook().getTitle(),
                        loan.getMember().getId(),
                        loan.getMember().getName(),
                        loan.getLoanDate(),
                        loan.getExpectedReturnDate(),
                        loan.getActualReturnDate(),
                        loan.getLoanStatus(),
                        loan.getFine()
                ))
                .toList();
    }

    public List<LoanResponse> findAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(loan -> new LoanResponse(
                        loan.getId(),
                        loan.getBook().getId(),
                        loan.getBook().getTitle(),
                        loan.getMember().getId(),
                        loan.getMember().getName(),
                        loan.getLoanDate(),
                        loan.getExpectedReturnDate(),
                        loan.getActualReturnDate(),
                        loan.getLoanStatus(),
                        loan.getFine()
                ))
                .toList();
    }

    public List<LoanResponse> getLoanMemberHistory(Long id) {
        return loanRepository.findLoansByMemberId(id)
                .stream()
                .map(loan -> new LoanResponse(
                        loan.getId(),
                        loan.getBook().getId(),
                        loan.getBook().getTitle(),
                        loan.getMember().getId(),
                        loan.getMember().getName(),
                        loan.getLoanDate(),
                        loan.getExpectedReturnDate(),
                        loan.getActualReturnDate(),
                        loan.getLoanStatus(),
                        loan.getFine()
                ))
                .toList();
    }
}
