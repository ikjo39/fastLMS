package com.zerobase.fastlms.banner.service;

import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;

import java.util.List;

public interface BannerService {

    /**
     * 배너 등록
     */
    boolean add(BannerInput parameter);

    /**
     * 배너 정보 수정
     */
    boolean set(BannerInput parameter);

    /**
     * 배너 목록
     */
    List<BannerDto> list(BannerParam parameter);

    /**
     * 배너 목록
     */
    List<BannerDto> bannerList();

    /**
     * 배너 상세 정보
     */
    BannerDto getById(long id);

    /**
     * 배너 내용 삭제
     */
    boolean del(String idList);
    /**
     * 전체 배너 목록 - 데이터가 많아지면 문제가 될 수도 있음
     */
    List<BannerDto> listAll();
}
