package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.History.entity.LoginHistory;
import com.zerobase.fastlms.History.repository.HistoryRepository;
import com.zerobase.fastlms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HistoryRepository historyRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication
    ) throws IOException, ServletException {

        LoginHistory loginHistory = LoginHistory.builder()
                .email(authentication.getName())
                .Ip(RequestUtils.getClientIP(request))
                .userAgent(RequestUtils.getUserAgent(request))
                .loginDt(LocalDateTime.now())
                .build();

        historyRepository.save(loginHistory);

        setDefaultTargetUrl("/");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
