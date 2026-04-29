package com.bookflow.service;

import com.bookflow.dto.request.MemberRequest;
import com.bookflow.dto.response.MemberResponse;
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

    public MemberResponse registerMember(MemberRequest memberRequest) {
        Member member = new Member();

        member.setName(memberRequest.name());
        member.setEmail(memberRequest.email());
        member.setPhone(memberRequest.phone());
        member.setRegistrationDate(LocalDate.now());

        member = memberRepository.save(member);

        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getRegistrationDate()
        );
    }

    public List<MemberResponse> listAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(member -> new MemberResponse(
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getPhone(),
                        member.getRegistrationDate()
                ))
                .toList();
    }

    public MemberResponse findMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado para o id: " + id));
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getRegistrationDate()
        );
    }

    public MemberResponse updateMember(Long id, MemberRequest memberDetails) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado para o id: " + id));

        if (memberDetails.email() != null) {
            existingMember.setEmail(memberDetails.email());
        }
        if (memberDetails.name() != null) {
            existingMember.setName(memberDetails.name());
        }
        if (memberDetails.phone() != null) {
            existingMember.setPhone(memberDetails.phone());
        }

        memberRepository.save(existingMember);

        return new MemberResponse(
                existingMember.getId(),
                existingMember.getName(),
                existingMember.getEmail(),
                existingMember.getPhone(),
                existingMember.getRegistrationDate()
        );
    }

    public void deleteMember(Long id){
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membro não encontrado para o id: " + id);
        }
        memberRepository.deleteById(id);
    }

}
