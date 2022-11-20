package com.zerobase.fastlms.History.service;

import com.zerobase.fastlms.History.dto.LoginHistoryDto;
import com.zerobase.fastlms.History.entity.LoginHistory;
import com.zerobase.fastlms.History.mapper.HistoryMapper;
import com.zerobase.fastlms.History.model.HistoryParam;
import com.zerobase.fastlms.History.repository.HistoryRepository;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {


    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public List<LoginHistoryDto> list(HistoryParam parameter, String id) {


        long totalCount = historyMapper.selectListCount(parameter, id);



        List<LoginHistoryDto> list = historyMapper.selectList(parameter, id);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (LoginHistoryDto x : list) {

                x.setTotalCount(totalCount);
                // 여기서 계산하기 애매함
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

    @Override
    public LoginHistoryDto getById(long id) {
        return historyRepository.findById(id).map(LoginHistoryDto::of).orElse(null);
    }

    @Override
    public LoginHistoryDto detail(long id) {
        Optional<LoginHistory> optionalLoginHistory = historyRepository.findById(id);

        if (!optionalLoginHistory.isPresent()) {
            return null;
        }

        LoginHistory loginHistory = optionalLoginHistory.get();

        return LoginHistoryDto.of(loginHistory);
    }
}
