package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.History.service.HistoryService;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.service.TakeCourseService;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {

    //    @Autowired 권장 X -> 생성자로 주입받게끔 함 -> @RequiredArgsConstructor
    private final MemberService memberService;
    private final TakeCourseService takeCourseService;
    private final HistoryService historyService;

    // /member/registor
    //    @RequestMapping(value="/member/register", method = RequestMethod.GET)
    @GetMapping("/member/register")
    public String register() {

        return "member/register";
    }

    // Request Web -> Server
    // Response Server -> Web

    //    @RequestMapping(value = "/member/register", method = RequestMethod.POST)
    @PostMapping("/member/register")
    public String registerSubmit(
            Model model
            , HttpServletRequest request
            , MemberInput parameter
    ) {
        // model : client에게 데이터를 내리기 위한 interface
        boolean result = memberService.register(parameter);
        model.addAttribute("result", result);

        return "member/register_complete";
    }


    /*
    *
        System.out.println("################## - 1");
        System.out.println(parameter.toString());
//        String userId = request.getParameter("userId");
//        String userName = request.getParameter("userName");
//        String password = request.getParameter("password");
//        String phone = request.getParameter("phone");
//
//        System.out.println("userId = " + userId);
//        System.out.println("userName = " + userName);
//        System.out.println("password = " + password);
//        System.out.println("phone = " + phone);

    * */

    // 프로토콜://도메인(IP)/news/
    // https://www.naver.com/cafe/detail.do?id=1111
    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");

        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);

        model.addAttribute("result", result);

        return "member/email_auth";
    }

    @GetMapping("/member/info")
    public String memberInfo(Model model, Principal principal) {

        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        model.addAttribute("detail", detail);


        return "member/info";
    }

    @PostMapping("/member/info")
    public String memberInfoSubmit(
            Model model
            , MemberInput parameter
            , Principal principal) {

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMember(parameter);

        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }


    @GetMapping("/member/password")
    public String memberPassword(Model model, Principal principal) {

        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        model.addAttribute("detail", detail);


        return "member/password";
    }

    @PostMapping("/member/password")
    public String memberPasswordSubmit(
            Model model
            , MemberInput parameter
            , Principal principal
    ) {

        String userId = principal.getName();
        /**
         * 관리자 쪽에서 쓰는거라 쓰기 애매함
         * -> 회원 password랑 입력된 값이 같을때만 수정 하려함
         * -> 검증 작업이 일어났다고 봐야함
         */
        parameter.setUserId(userId);


        ServiceResult serviceResult = memberService.updateMemberPassword(parameter);
        if (!serviceResult.isResult()) {
            model.addAttribute("message", serviceResult.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }

    @GetMapping("/member/takecourse")
    public String memberTakeCourse(Model model, Principal principal) {

        String userId = principal.getName();

        List<TakeCourseDto> list = takeCourseService.myCourse(userId);

        model.addAttribute("list", list);

        return "member/takecourse";
    }

    @RequestMapping("/member/login")
    public String login() {

        return "member/login";
    }

    @GetMapping("/member/find/password")
    public String findPassword() {
        return "member/find_password";
    }

    @PostMapping("/member/find/password")
    public String findPasswordSubmit(
            Model model
            , ResetPasswordInput parameter
    ) {

        boolean result = false;
        try {
            result = memberService.sendResetPassword(parameter);
        } catch (Exception e) {

        }
        model.addAttribute("result", result);

//        return "index"; - 이러면 주소는 동일한데 뷰만 바뀜
//        return "redirect:/";
        return "member/find_password_result";
    }

    @GetMapping("/member/reset/password")
    public String resetPassword(Model model, HttpServletRequest request) {
        String uuid = request.getParameter("id");

        boolean result = memberService.checkResetPassword(uuid);

        model.addAttribute("result", result);

        return "member/reset_password";
    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {

        boolean result = false;
        try {
            result = memberService.resetPassword(parameter.getId(), parameter.getPassword());
        } catch (Exception e) {

        }
        model.addAttribute("result", result);

        return "member/reset_password_result";

    }

    @GetMapping("/member/withdraw")
    public String memberWithdraw(Model model) {

        return "member/withdraw";
    }
    @PostMapping("/member/withdraw")
    public String memberWithdrawSubmit(
            Model model
            , MemberInput parameter
            , Principal principal
    ) {
        String userId = principal.getName();

        ServiceResult result = memberService.withdraw(userId, parameter.getPassword());
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/logout";
    }
}
