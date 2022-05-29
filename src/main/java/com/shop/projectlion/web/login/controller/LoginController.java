package com.shop.projectlion.web.login.controller;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login/loginform";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());
        return "login/registerform";
    }

    @PostMapping("/register")
    public String newMember(@Valid MemberRegisterDto memberRegisterDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "login/registerform";
        }

        try{
            Member member = Member.createMember(memberRegisterDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "login/registerform";
        }

        return "redirect:/login";
    }

//    public void validateSamePassword(MemberRegisterDto memberRegisterDto){
//        String password = memberRegisterDto.getPassword();
//        String password2 = memberRegisterDto.getPassword2();
//
//        if(!password.equals(password2)){
//            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
//        }
//    }

}
