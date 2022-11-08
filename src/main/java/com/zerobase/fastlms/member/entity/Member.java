package com.zerobase.fastlms.member.entity;

import com.zerobase.fastlms.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder // 쉽게 값을 채울 수 있음
@Data
@Entity
public class Member implements MemberCode{
    @Id
    private String userId;

    private String userName;
    private String phoneNumber;
    private String password;
    private LocalDateTime regDt;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;

    // 관리자 여부를 지정할꺼냐?
    // 회원에 따른 ROLE을 지정할 거냐
    // 1. 준회원/정회원/특별회원/관리자
    // ROLE_SEMI-USER/ROLE_USER/ROLE_SPECIAL_USER/ROLE_ADMIN
    // 2. 관리자 별도
    @Enumerated(EnumType.STRING)
    private Role role;

    // 이용가능한 상태, 정지 상태
    private String userStatus;
}
