package com.zerobase.fastlms.main.controller;

// 만든 목적 -> 매핑 목적:
// 주소와 물리적인 파일 매핑
//  누가 어디서 하냐
/*
 *   하나의 주소에 대해서
 *   클래스(비효율), 속성, 메서드(이게 맞음) 중 하나가 함
 * */

import com.zerobase.fastlms.component.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller //@RestController 보다 @Controller를 씀, 이때 문자열을 return하면 애러가 뜸
// Response 객체를 이용해 return 할 수 있음
public class MainController {

    private final MailComponents mailComponents;

    @RequestMapping("/")
    public String index(HttpServletRequest request) {

//        String userAgent = RequestUtils.getUserAgent(request);
//        String clientIp = RequestUtils.getClientIP(request);
//
//        log.info(userAgent);
//        log.info(clientIp);

//        String email = "chi4321@naver.com";
//        String subject = "안녕하세요 제로베이스입니다.";
//        String text = "<p>안녕하세요.</p> <p>반갑습니다.</>";
//
//        mailComponents.sendMail(email, subject, text);
        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied() {

        String email = "chi4321@naver.com";
        String subject = "안녕하세요 제로베이스입니다.";
        String text = "<p>안녕하세요.</p> <p>반갑습니다.</>";

        mailComponents.sendMail(email, subject, text);
        return "error/denied";
    }

}
/*
 * // request -> WEB -> Server
 * // response -> Server -> WEB
 *
 * 스프링 ->  MVC (View -> 템플릿엔진 화면에 내용을 출력 (html))
 * // .NET ->  MVC (View -> 출력)
 * // python django -> MTV (Template -> 화면출력)
 * // 백엔드 과정 -> V(화면에 보여진 부분) -> FE임
 * // V -> HTML => 웹 페이지
 * // V -> json => API
 * <p>
 * tymeleaf 에서 문자열 -> html 로 가도록 이루어져 있음
 */