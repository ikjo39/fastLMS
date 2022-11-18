package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;

import java.util.List;

public interface CourseService {

    /**
     * 강좌 등록
     */
    boolean add(CourseInput parameter);

    /**
     * 강좌 정보 수정
     */
    boolean set(CourseInput parameter);

    /**
     * 강좌 목록
     */
    List<CourseDto> list(CourseParam parameter);

    /**
     * 강좌 상세 정보
     */
    CourseDto getById(long id);

    /**
     * 강좌 내용 삭제
     */
    boolean del(String idList);

    /**
     * 강좌 목록 - front용
     */
    List<CourseDto> frontList(CourseParam parameter);

    /**
     * 상세 강좌 목록 - front용
     */
    CourseDto frontDetail(long id);

    /**
     * 수강 신청 - front용
     */
    ServiceResult req(TakeCourseInput parameter);

    /**
     * 전체 강좌 목록 - 데이터가 많아지면 문제가 될 수도 있음
     */
    List<CourseDto> listAll();
}
