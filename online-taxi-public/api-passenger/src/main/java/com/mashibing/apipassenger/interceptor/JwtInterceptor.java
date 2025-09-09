package com.mashibing.apipassenger.interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import net.sf.json.JSONObject;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.dto.TokenResult;
import org.mashibing.internalcommon.util.JwtUtil;
import org.mashibing.internalcommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-09 16:27
 */
public class JwtInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        boolean result=true;
//
//        String resultString="";
//
//        String token = request.getHeader("Authorization"); //Token
//
//        try {
//            JwtUtil.parseToken(token);
//        }catch (SignatureVerificationException e){
//            resultString="token sign error";
//            result=false;
//        }catch (TokenExpiredException e){
//            resultString="token time out";
//            result=false;
//        }catch (AlgorithmMismatchException e){
//            resultString="token AlgorithmMismatchException error";
//            result=false;
//        }catch (Exception e){
//            resultString="token error";
//            result=false;
//        }
//        if(!result){
//            PrintWriter out=response.getWriter();
//            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
//        }
//
//        return HandlerInterceptor.super.preHandle(request, response, handler);
//    }

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 预检请求直接放行（避免拦 CORS）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || StringUtils.isBlank(token)) {
            writeJson(response, 401, ResponseResult.fail("missing token"));
            return false; // 关键：写了就返回
        }
        // 去掉 Bearer 前缀
        token = token.replaceFirst("(?i)^Bearer\\s+", "");
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtil.parseToken(token); // 你的解析方法，注意用 asString() 取 claim
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity);
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(tokenRedis)) {//判断是否为空
                writeJson(response, 401, ResponseResult.fail("token error"));
                return false;
            }else{
                if (!token.trim().equals(tokenRedis.trim())){
                    writeJson(response, 401, ResponseResult.fail("token error"));
                    return false;
                }
            }
            return true;               // 校验通过，放行
        } catch (com.auth0.jwt.exceptions.SignatureVerificationException e) {
            writeJson(response, 401, ResponseResult.fail("token sign error"));
            return false;
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {
            writeJson(response, 401, ResponseResult.fail("token time out"));
            return false;
        } catch (com.auth0.jwt.exceptions.AlgorithmMismatchException e) {
            writeJson(response, 401, ResponseResult.fail("token AlgorithmMismatchException error"));
            return false;
        } catch (Exception e) {
            writeJson(response, 401, ResponseResult.fail("token error"));
            return false;
        }
    }

    private void writeJson(HttpServletResponse resp, int status, Object body) throws IOException {
        resp.setStatus(status);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        // 如果你项目里有 Jackson，就用它序列化
        String json = com.fasterxml.jackson.databind.json.JsonMapper.builder().build().writeValueAsString(body);
        resp.getWriter().write(json);
        resp.getWriter().flush();
    }
}


