package org.mashibing.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-09 12:15
 */

@Data
public class PassengerUser {

    private int id;
    private String passengerPhone; //乘客号码
    private String passengerName; //乘客姓名
    private int passengerGender; //性别
    private int state;//状态 1启用 0 关闭
    private LocalDateTime gmpCreate; //创建时间
    private LocalDateTime gmpModified;//更新时间
    private String profilePhoto;//个人资料

}
