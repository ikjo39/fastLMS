package com.zerobase.fastlms.History.mapper;

import com.zerobase.fastlms.History.dto.LoginHistoryDto;
import com.zerobase.fastlms.History.model.HistoryParam;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {


    long selectListCount(HistoryParam parameter, String id);
    List<LoginHistoryDto> selectList(HistoryParam parameter, String id);

}
