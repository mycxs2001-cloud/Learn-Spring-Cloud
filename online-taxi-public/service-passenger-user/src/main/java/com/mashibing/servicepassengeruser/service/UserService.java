package com.mashibing.servicepassengeruser.service;

import com.mashibing.servicepassengeruser.dto.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-08 18:41
 */
@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passGengerPhone) {

        System.out.println("user Service 呗调用 手机号:" + passGengerPhone);
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passGengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);

        //判断用户信息是否存在
        if (passengerUsers.isEmpty()) {
            //如果没有用户就新增
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerPhone(passGengerPhone);
            passengerUser.setPassengerGender(1);
            passengerUser.setState(0);

            LocalDateTime localDateTime = LocalDateTime.now();

            passengerUser.setGmpCreate(localDateTime);
            passengerUser.setGmpModified(localDateTime);

            passengerUserMapper.insert(passengerUser);

        }
        //如果不存在,插入数据

        return ResponseResult.success();
    }

}
