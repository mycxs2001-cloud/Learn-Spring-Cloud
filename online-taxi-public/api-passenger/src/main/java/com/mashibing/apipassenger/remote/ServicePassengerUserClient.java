package com.mashibing.apipassenger.remote;

import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-09 13:38
 */
@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

 @RequestMapping(method = RequestMethod.POST,value="/user")
  ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

}
