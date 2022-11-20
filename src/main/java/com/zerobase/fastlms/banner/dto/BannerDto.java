package com.zerobase.fastlms.banner.dto;

import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerDto {

    Long id;

    String alterText;
    String url;
    String target;

    String sequence;
    LocalDateTime regDt; // 등록일
    LocalDateTime udtDt;    // 수정일
    boolean open;

    String fileName;
    String urlFileName;

    long totalCount;
    long seq;



    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .url(banner.getUrl())
                .target(banner.getTarget())
                .sequence(banner.getSequence())
                .regDt(banner.getRegDt())
                .udtDt(banner.getUdtDt())
                .open(banner.isOpen())
                .fileName(banner.getFileName())
                .urlFileName(banner.getUrlFileName())
                .build();

    }

    public static List<BannerDto> of(List<Banner> banners) {

        if (banners == null) {
            return null;
        }

        List<BannerDto> bannerDtoList = new ArrayList<>();

        for (Banner x : banners) {
            bannerDtoList.add(BannerDto.of(x));
        }

        return bannerDtoList;
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        return regDt != null ? regDt.format(formatter) : "";
    }

    public String getUdtDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        return udtDt != null ? udtDt.format(formatter) : "";
    }
}
