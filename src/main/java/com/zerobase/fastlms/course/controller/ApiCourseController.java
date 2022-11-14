package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.common.model.ResponseResult;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
public class ApiCourseController extends BaseController {

    private final CourseService courseService;
    private final CategoryService categoryService;


    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(Model model
            , @RequestBody TakeCourseInput parameter
            , Principal principal
    ) {

        // 내 로그인한 아이디가 있어야함
        parameter.setUserId(principal.getName());
        ServiceResult result = courseService.req(parameter);


        if (!result.isResult()) {
            // badRequest() -> 이슈가 있음
            ResponseResult responseResult = new ResponseResult(false, result.getMessage());

            return ResponseEntity.ok().body(responseResult);
        }
        ResponseResult responseResult = new ResponseResult(true);

        return ResponseEntity.ok().body(responseResult);
    }

}

/*
 * client 쪽에서 고민스럽긴 한데 웹을 하는 부분에서 기본적인것만 알고 가면 됨
 * 백엔드-서버쪽에서 일을 하고 client와 구분하기 위해 api서버를 구성함
 * 실직적으로 안한거 같은 느낌이 들어 webForm 에서 진해함
 *
 * API 에 대해 기본적인 이슈가 있어서 다하기보다 course 신청에서만 함
 *
 * 원래는 - Controller와 view 템플릿을 리턴하지만
 * API 는 데이터만 바로 리턴함
 * - Controller - viewPage 대신 restController - jsonBody 형태 씀
 * */
// form submit 말고 jsonbody로 받기에 @RequestBody 지정
