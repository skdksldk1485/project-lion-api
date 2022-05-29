package com.shop.projectlion.domain.member.service;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import com.shop.projectlion.domain.member.validation.MemberValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidation memberValidation;

    public Member saveMember(Member member){
        memberValidation.validateDuplicateMember(member);
        return memberRepository.save(member);
    }

}
