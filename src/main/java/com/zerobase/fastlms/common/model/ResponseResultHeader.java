package com.zerobase.fastlms.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseResultHeader {
    boolean result;
    String message;

    public ResponseResultHeader(boolean result) {
        this.result = result;
    }

    public ResponseResultHeader(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
