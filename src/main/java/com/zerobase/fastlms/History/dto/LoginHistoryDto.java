package com.zerobase.fastlms.History.dto;

import com.zerobase.fastlms.History.entity.LoginHistory;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.member.entity.Member;
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
public class LoginHistoryDto {

    long id;
    String email;
    LocalDateTime loginDt;

    String Ip;
    String userAgent;

    long totalCount;
    long seq;

    public static LoginHistoryDto of(LoginHistory loginHistory) {
        return LoginHistoryDto.builder()
                .id(loginHistory.getId())
                .loginDt(loginHistory.getLoginDt())
                .Ip(loginHistory.getIp())
                .userAgent(loginHistory.getUserAgent())
                .build();
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        return loginDt != null ? loginDt.format(formatter) : "";
    }


}
