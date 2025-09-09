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

    VERIFICATION_ERROR(1099, "验证码不正确"),

    /**
     * Token不正确
     */
    TOKEN_ERROR(1199, "Token不正确"),

    /**
     * 成功
     */
    SUCCESS(1, "success"),
    /**
     * 失败
     */
    FAIL(0, "fail");

    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
