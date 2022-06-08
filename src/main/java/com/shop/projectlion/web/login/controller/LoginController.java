package com.shop.projectlion.web.login.controller;

import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import com.shop.projectlion.web.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String isError, Model model) {
        if(Boolean.valueOf(isError)) {
            model.addAttribute("loginError", ErrorCode.LOGIN_ERROR.getMessage());
        }
        return "login/loginform";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());
        return "login/registerform";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("memberRegisterDto") MemberRegisterDto memberRegisterDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "/login/registerform";
        } else if(!StringUtils.equals(memberRegisterDto.getPassword(), memberRegisterDto.getPassword2())) {
            bindingResult.reject("mismatchedPassword", ErrorCode.MISMATCHED_PASSWORD.getMessage());
            return "login/registerform";
        }

        try {
            loginService.registerMember(memberRegisterDto);
        } catch (BusinessException e) {
            e.printStackTrace();
            bindingResult.reject("alreadyMember", e.getMessage());
            return "login/registerform";
        }

        return "redirect:/login";
    }
}
