package com.bookflow.service;

import com.bookflow.exception.ResourceNotFoundException;
import com.bookflow.model.Member;
import com.bookflow.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member registerMember(Member member) {
        member.setRegistrationDate(LocalDate.now());
        return memberRepository.save(member);
    }

    public List<Member> listAllMembers() {
        return memberRepository.findAll();
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado para o id: " + id));
    }

    public Member updateMember(Long id, Member memberDetails) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado para o id: " + id));

        if (memberDetails.getEmail() != null) {
            existingMember.setEmail(memberDetails.getEmail());
        }
        if (memberDetails.getName() != null) {
            existingMember.setName(memberDetails.getName());
        }
        if (memberDetails.getPhone() != null) {
            existingMember.setPhone(memberDetails.getPhone());
        }

        return memberRepository.save(existingMember);
    }

    public void deleteMember(Long id){
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membro não encontrado para o id: " + id);
        }
        memberRepository.deleteById(id);
    }

}
