package com.bookflow.controller;

import com.bookflow.dto.request.MemberRequest;
import com.bookflow.dto.response.MemberResponse;
import com.bookflow.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse registerMember(@RequestBody MemberRequest memberRequest){
        return memberService.registerMember(memberRequest);
    }

    @GetMapping
    public List<MemberResponse> listAllMembers() {
        return memberService.listAllMembers();
    }

    @GetMapping("/{id}")
    public MemberResponse findMemberById(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @PutMapping("/{id}")
    public MemberResponse updateMember(@PathVariable Long id, @RequestBody MemberRequest memberRequest) {
        return memberService.updateMember(id, memberRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}
