package com.shop.projectlion.web.login.service;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @Transactional
    public void registerMember(MemberRegisterDto memberRegisterDto) {
        Member member = memberRegisterDto.toEntity(passwordEncoder);
        Member saveMember = Member.createMember(member);
        memberService.registerMember(saveMember);
    }


}
