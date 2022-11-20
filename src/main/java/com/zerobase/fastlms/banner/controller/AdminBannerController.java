package com.zerobase.fastlms.banner.controller;

import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.banner.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
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
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/*
 *   카테고리는 강의쪽에 있어야하지만 복잡하지 않게 쪼갰음
 * */
@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminBannerController extends BaseController {

    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String list(Model model, BannerParam parameter) {

        parameter.init();
        List<BannerDto> bannerList = bannerService.list(parameter);

        long totalCount = 0;

        if (!CollectionUtils.isEmpty(bannerList)) {
            totalCount = bannerList.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", bannerList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/banner/list";
    }

    /*
     *   상세에서 수정할 수 있어야함 - edit.do, add.do는 동일함 - id가 있냐없냐임
     *   물론 특정데이터에 따라 한번 입력되면 수정이 안되는건 있을 수 있는데 이건 배제함
     * */
    @GetMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String add(Model model, HttpServletRequest request, BannerInput parameter) {

        boolean editMode = request.getRequestURI().contains("/edit.do");
        BannerDto detail = new BannerDto();

        if (editMode) {
            long id = parameter.getId();
            BannerDto existBanner = bannerService.getById(id);
            if (existBanner == null) {
                // error 처리
                model.addAttribute("message", "배너 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existBanner;
        }
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);
        return "admin/banner/add";
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


    @PostMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String addSubmit(
            Model model
            , HttpServletRequest request
            , BannerInput parameter
            , MultipartFile file
    ) {
        String saveFileName = "";
        String urlFileName = "";
        if (file.getSize() != 0) {
            // 원래는 환경파일 빼주고 그래야하는데 시간이 많이 걸려서 이렇게 함
            String originalFilename = file.getOriginalFilename();
            String baseLocalPath = "C:\\dev\\springStudy\\fastLMS\\fastLMS\\files";
            String baseUrlPath = "/files";
            String[] arrFileName = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFileName = arrFileName[0];
            urlFileName = arrFileName[1];

            File newFile = new File(saveFileName);

            try {
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(newFile.toPath()));
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
            BannerDto existBanner = bannerService.getById(id);
            if (existBanner == null) {
                // error 처리
                model.addAttribute("message", "배너 정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = bannerService.set(parameter);
        } else {
            System.out.println(request.getParameter("target"));
            System.out.println(parameter.getTarget());
            boolean result = bannerService.add(parameter);
        }


        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/admin/banner/delete.do")
    public String del(Model model, HttpServletRequest request, CourseInput parameter) {

        boolean result = bannerService.del(parameter.getIdList());

        return "redirect:/admin/banner/list.do";
    }
}
