package com.shop.projectlion.domain.member.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
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

    @Builder
    public Member(MemberType memberType, String email, String password, String memberName, Role role) {
        this.memberType = memberType;
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.role = role;
    }

    public static Member createMember(Member member) {
        return Member.builder()
                .memberType(member.getMemberType())
                .email(member.getEmail())
                .password(member.getPassword())
                .memberName(member.getMemberName())
                .role(member.getRole())
                .build();
    }
}
