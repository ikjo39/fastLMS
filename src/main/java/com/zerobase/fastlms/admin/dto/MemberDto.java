package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    LocalDateTime udtDt;

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


    String zipcode;
    String addr;
    String addrDetail;

    public static MemberDto of(Member member) {

        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phoneNumber(member.getPhoneNumber())
                //.password(member.getPassword())
                .regDt(member.getRegDt())
                .udtDt(member.getUdtDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .role(member.getRole())
                .userStatus(member.getUserStatus())
                .zipcode(member.getZipcode())
                .addr(member.getAddr())
                .addrDetail(member.getAddrDetail())
                .build();
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        return regDt != null ? regDt.format(formatter) : "";
    }

    public String getUdtDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        return udtDt != null ? udtDt.format(formatter) : "";
    }
}
