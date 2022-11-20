package com.zerobase.fastlms.History.service;

import com.zerobase.fastlms.History.dto.LoginHistoryDto;
import com.zerobase.fastlms.History.entity.LoginHistory;
import com.zerobase.fastlms.History.model.HistoryParam;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;

import java.util.List;

public interface HistoryService {

    List<LoginHistoryDto> list(HistoryParam historyParam, String id);
    /**
     * 강좌 상세 정보
     */
    LoginHistoryDto getById(long id);

    LoginHistoryDto detail(long id);
}
