package com.shop.projectlion.domain.member.entity;

import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.global.config.BaseEntity;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String memberName;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(nullable = false)
    private String password;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime tokenExpirationTime;

    public static Member createMember(MemberRegisterDto memberRegisterDto, PasswordEncoder passwordEncoder){
        Member member = Member.builder()
                .email(memberRegisterDto.getEmail())
                .memberName(memberRegisterDto.getName())
                .memberType(MemberType.GENERAL)
                .password(passwordEncoder.encode(memberRegisterDto.getPassword()))
                .refreshToken(null)
                .role(Role.ADMIN)
                .tokenExpirationTime(null)
                .build();
        return member;
    }
}
