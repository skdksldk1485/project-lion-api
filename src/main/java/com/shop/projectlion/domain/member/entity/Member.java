package com.shop.projectlion.domain.member.entity;

import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberType memberType;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(length = 250)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
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
