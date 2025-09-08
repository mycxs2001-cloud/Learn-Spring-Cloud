package org.mashibing.internalcommon.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-05 18:22
 */
public enum CommonStatusEnum {

    /**
     * 成功
     */
    SUCCESS(1,"success"),
    /**
     * 失败
     */
    FAIL(0,"fail");

    @Getter
    private int code;
    @Getter
    private String value;

     CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
