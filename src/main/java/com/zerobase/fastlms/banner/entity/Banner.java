package com.zerobase.fastlms.banner.entity;

import com.zerobase.fastlms.banner.type.TargetCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Banner implements TargetCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alterText;
    private String url;
    private String target;

    private String sequence;
    private LocalDateTime regDt;    // 등록일
    private LocalDateTime udtDt;    // 수정일
    private boolean open;

    private String fileName;
    private String urlFileName;
}
