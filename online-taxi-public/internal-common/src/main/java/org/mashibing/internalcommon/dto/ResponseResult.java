package org.mashibing.internalcommon.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.mashibing.internalcommon.constant.CommonStatusEnum;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-05 18:28
 */
@Data
@Accessors(chain = true)
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;


    /**
     * 成功响应的方法
     * @return
     * @param <T>
     */
    public static <T> ResponseResult success(){
        return  new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue());
    }

    /**
     * 成功的方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }


    /**
     * 统一的失败
     *
     * @param data
     * @return
     */
    public static <T>  ResponseResult fail(T data) {
        return new ResponseResult().setData(data);
    }

    /**
     * 自定义失败
     *
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult fail(int code, String message) {
        return new ResponseResult().setCode(CommonStatusEnum.FAIL.getCode()).setMessage(CommonStatusEnum.FAIL.getValue());
    }

    /**
     * 失败 自定义失败、错误码、提示信息、具体错误
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseResult fail(int code, String message, String data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }


}
