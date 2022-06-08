package com.shop.projectlion.domain.member.service;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import com.shop.projectlion.domain.member.validation.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    @Transactional
    public void registerMember(Member member) {
        validateRegisterMember(member);
        memberRepository.save(member);
    }

    public void validateRegisterMember(Member member) {
        memberValidator.validateDuplicateMember(member.getEmail());
    }

    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

}
