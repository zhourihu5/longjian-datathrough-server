package com.longfor.longjian.datathrough.app.controller;

import com.longfor.longjian.common.base.LjBaseResponse;
import com.longfor.longjian.datathrough.app.appService.login.LoginService;
import com.longfor.longjian.datathrough.dto.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录认证  分PC和APP
 * Created by Wang on 2018/11/18.
 */
@RestController
@Slf4j
@RequestMapping("uc/")
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * PC登录接口一
     *
     * @param username
     * @param password
     * @param rememberMe
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "user/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LjBaseResponse baseMurphy(@RequestParam(value = "user_name") String username,
                                     @RequestParam(value = "password") String password,
                                     @RequestParam(value = "remember_me") int rememberMe,
                                     HttpServletRequest request, HttpServletResponse response) {
        LoginDto loginDto = new LoginDto();

        loginDto.setUser_name(username);
        loginDto.setPassword(password);
        loginDto.setRemember_me(rememberMe);
        loginDto.setLoginType(1);

        return loginService.pcLogin(loginDto, request, response);
    }

    /**
     * PC登录接口二
     *
     * @param additionalCheckKey
     * @param phone
     * @param email
     * @param credentials_no
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "pc/baseMurphyAdditionalCheck", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LjBaseResponse baseMurphyAdditionalCheck(@RequestParam(value = "additionalCheckKey") long additionalCheckKey,
                                                    @RequestParam(value = "phone") String phone,
                                                    @RequestParam(value = "email") String email,
                                                    @RequestParam(value = "credentials_no") String credentials_no,
                                                    HttpServletRequest request, HttpServletResponse response) {
        LoginDto loginDto = new LoginDto();

        loginDto.setAdditionalCheckKey(additionalCheckKey);
        loginDto.setPhone(phone);
        loginDto.setEmail(email);
        loginDto.setCredentials_no(credentials_no);
        loginDto.setLoginType(2);

        return loginService.pcLogin(loginDto, request, response);
    }

    /**
     * PC登录接口三
     *
     * @param additionalCheckKey
     * @param selectTenantId
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "pc/baseMurphySelectTenant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LjBaseResponse baseMurphyAdditionalCheck(@RequestParam(value = "additionalCheckKey") long additionalCheckKey,
                                                    @RequestParam(value = "selectTenantId") long selectTenantId,
                                                    HttpServletRequest request, HttpServletResponse response) {
        LoginDto loginDto = new LoginDto();

        loginDto.setAdditionalCheckKey(additionalCheckKey);
        loginDto.setSelectTenantId(selectTenantId);
        loginDto.setLoginType(3);

        return loginService.pcLogin(loginDto, request, response);
    }


    /**
     * APP登录接口一
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "app/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LjBaseResponse appBaseMurphy(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            HttpServletRequest request, HttpServletResponse response) {
        LoginDto loginDto = new LoginDto();

        loginDto.setUser_name(username);
        loginDto.setPassword(password);

        return loginService.appLogin(loginDto, request, response);
    }

    /**
     * APP登录接口二
     *
     * @param additionalCheckKey
     * @param phone
     * @param email
     * @param credentials_no
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "app/baseMurphyAdditionalCheck", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LjBaseResponse appBaseMurphyAdditionalCheck(@RequestParam(value = "additionalCheckKey") long additionalCheckKey,
                                                       @RequestParam(value = "phone") String phone,
                                                       @RequestParam(value = "email") String email,
                                                       @RequestParam(value = "credentials_no") String credentials_no,
                                                       HttpServletRequest request, HttpServletResponse response) {
        LoginDto loginDto = new LoginDto();

        loginDto.setAdditionalCheckKey(additionalCheckKey);
        loginDto.setPhone(phone);
        loginDto.setEmail(email);
        loginDto.setCredentials_no(credentials_no);
        loginDto.setLoginType(2);

        return loginService.appLogin(loginDto, request, response);
    }

    /**
     * APP 登录接口三
     *
     * @param additionalCheckKey
     * @param selectTenantId
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "app/baseMurphySelectTenant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LjBaseResponse aapBaseMurphyAdditionalCheck(@RequestParam(value = "additionalCheckKey") long additionalCheckKey,
                                                       @RequestParam(value = "selectTenantId") long selectTenantId,
                                                       HttpServletRequest request, HttpServletResponse response) {
        LoginDto loginDto = new LoginDto();

        loginDto.setAdditionalCheckKey(additionalCheckKey);
        loginDto.setSelectTenantId(selectTenantId);
        loginDto.setLoginType(3);

        return loginService.appLogin(loginDto, request, response);
    }

}
