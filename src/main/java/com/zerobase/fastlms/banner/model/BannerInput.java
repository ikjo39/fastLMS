package com.zerobase.fastlms.banner.model;

import com.zerobase.fastlms.banner.type.TargetCode;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class BannerInput implements TargetCode {

    long id;
    String alterText;
    String url;
    String target;

    String sequence;
    LocalDateTime regDt;    // 등록일
    LocalDateTime udtDt;    // 수정일
    boolean open;

    //삭제를 위한
    String idList;
//    List<String> ids; 도 됨

    // ADD
    String fileName;
    String urlFileName;
}
