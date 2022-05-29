package com.shop.projectlion.domain.member.validation;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberValidation {
    
    private final MemberRepository memberRepository;
    
    public void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
