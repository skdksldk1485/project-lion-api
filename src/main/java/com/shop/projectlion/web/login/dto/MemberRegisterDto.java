package com.shop.projectlion.web.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRegisterDto {

    private String name;

    private String email;

    private String password;

    private String password2;

}