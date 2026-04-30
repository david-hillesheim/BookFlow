package com.bookflow.controller;

import com.bookflow.dto.request.LoanRequest;
import com.bookflow.dto.response.LoanResponse;
import com.bookflow.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse registerLoan(@RequestBody LoanRequest loanRequest){
        return loanService.registerLoan(loanRequest);
    }

    @PostMapping ("/{id}/return")
    public LoanResponse returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    @GetMapping
    public List<LoanResponse> findAllLoans() {
        return loanService.findAllLoans();
    }

    @GetMapping("/overdue")
    public List<LoanResponse> findOverdueLoans() {
        return loanService.findOverdueLoans();
    }

    @GetMapping ("/member/{id}")
    public List<LoanResponse> getLoanMemberHistory(@PathVariable Long id) {
        return loanService.getLoanMemberHistory(id);
    }
}