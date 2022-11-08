package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {
    // JPA는 rawData

    String userId;
    String userName;
    String phoneNumber;
    String password;
    LocalDateTime regDt;

    LocalDateTime emailAuthDt;
    String emailAuthKey;
    boolean emailAuthYn;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDt;

    Role role;
    String userStatus;

    // query  추가할 것임
    long totalCount;
    long seq;

    public static MemberDto of(Member member) {

        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phoneNumber(member.getPhoneNumber())
                //.password(member.getPassword())
                .regDt(member.getRegDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .role(member.getRole())
                .userStatus(member.getUserStatus())
                .build();
    }

}
