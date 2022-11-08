package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.type.Role;
import lombok.Data;

import java.time.LocalDateTime;

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

    // query  추가할 것임
    long totalCount;
    long seq;
}
