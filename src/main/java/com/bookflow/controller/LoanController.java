package com.bookflow.controller;

import com.bookflow.model.Loan;
import com.bookflow.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public Loan registerLoan(@RequestBody Loan loan){
        return loanService.registerLoan(loan);
    }

    @PutMapping ("/{id}/return")
    public Loan returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    @GetMapping
    public List<Loan> findOverdueLoans() {
        return loanService.findOverdueLoans();
    }
}
