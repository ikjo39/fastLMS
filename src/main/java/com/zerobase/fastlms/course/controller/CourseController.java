package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/*
 *   카테고리는 강의쪽에 있어야하지만 복잡하지 않게 쪼갰음
 * */
@RequiredArgsConstructor
@Controller
public class CourseController extends BaseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    /*
    * @RequestParam, CourseParam은 같이쓰기 애매함
    * */
    @GetMapping("/course")
    public String course(Model model, CourseParam parameter) {

        List<CourseDto> list = courseService.frontList(parameter);

        model.addAttribute("list", list);

        int courseTotalCount = 0;

        List<CategoryDto> categoryDtoList
                = categoryService.frontList(CategoryDto.builder().build());

        if (categoryDtoList != null) {
            for (CategoryDto x : categoryDtoList) {
                courseTotalCount += x.getCourseCount();
            }
        }

        model.addAttribute( "categoryList", categoryDtoList);
        model.addAttribute( "courseTotalCount", courseTotalCount);

        return "course/index";
    }


    @GetMapping("/course/{id}")
    public String courseDetail(Model model, CourseParam parameter) {

        CourseDto detail = courseService.frontDetail(parameter.getId());
        model.addAttribute("detail", detail);
        return "course/detail";
    }
}
