package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/*
 *   카테고리는 강의쪽에 있어야하지만 복잡하지 않게 쪼갰음
 * */
@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParam parameter) {

        parameter.init();
        List<CourseDto> courseList = courseService.list(parameter);

        long totalCount = 0;

        if (!CollectionUtils.isEmpty(courseList)) {
            totalCount = courseList.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/course/list";
    }

    /*
     *   상세에서 수정할 수 있어야함 - edit.do, add.do는 동일함 - id가 있냐없냐임
     *   물론 특정데이터에 따라 한번 입력되면 수정이 안되는건 있을 수 있는데 이건 배제함
     * */
    @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request, CourseInput parameter) {

        // 카테고리 정보 내려줘야 함
        model.addAttribute("category", categoryService.list());


        boolean editMode = request.getRequestURI().contains("/edit.do");
        CourseDto detail = new CourseDto();

        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existCourse;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);
        return "admin/course/add";
    }

    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s\\%d\\", baseLocalPath, now.getYear())
                , String.format("%s\\%d\\%02d\\", baseLocalPath, now.getYear(), now.getMonthValue())
                , String.format(
                "%s\\%d\\%02d\\%02d\\"
                , baseLocalPath
                , LocalDate.now().getYear()
                , LocalDate.now().getMonthValue()
                , LocalDate.now().getDayOfMonth()
        )
        };

        String urlDir = String.format(
                "%s/%d/%02d/%02d/"
                , baseUrlPath
                , LocalDate.now().getYear()
                , LocalDate.now().getMonthValue()
                , LocalDate.now().getDayOfMonth()
        );

        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFileName = String.format("%s%s", dirs[2], uuid);
        String newUrlFileName = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFileName += "." + fileExtension;
            newUrlFileName += "." + fileExtension;
        }

        return new String[]{newFileName, newUrlFileName};
    }


    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String addSubmit(
            Model model
            , HttpServletRequest request
            , CourseInput parameter
//            , List<MultipartFile>
            , MultipartFile file
    ) {

        String saveFileName = "";
        String urlFileName = "";
        if (file != null) {
            // 원래는 환경파일 빼주고 그래야하는데 시간이 많이 걸려서 이렇게 함
            String originalFilename = file.getOriginalFilename();
            String baseLocalPath = "C:\\dev\\springStudy\\fastLMS\\fastLMS\\files";
            String baseUrlPath = "/files";
            String[] arrFileName = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFileName = arrFileName[0];
            urlFileName = arrFileName[1];

            File newFile = new File(saveFileName);

            try {
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (Exception e) {
                log.info("##########################");
                log.info(e.getMessage());
            }

            parameter.setFileName(saveFileName);
            parameter.setUrlFileName(urlFileName);

        }

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode) { // validation check
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌 정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.set(parameter);
        } else {
            boolean result = courseService.add(parameter);
        }


        return "redirect:/admin/course/list.do";
    }

    @PostMapping("/admin/course/delete.do")
    public String del(Model model, HttpServletRequest request, CourseInput parameter) {

        boolean result = courseService.del(parameter.getIdList());

        return "redirect:/admin/course/list.do";
    }
}
