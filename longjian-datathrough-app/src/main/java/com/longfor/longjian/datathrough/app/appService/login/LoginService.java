package com.longfor.longjian.datathrough.app.appService.login;

import com.longfor.longjian.common.base.LjBaseResponse;
import com.longfor.longjian.datathrough.dto.LoginDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Wang on 2018/11/18.
 */
public interface LoginService {

    LjBaseResponse pcLogin(LoginDto dto, HttpServletRequest request, HttpServletResponse response);

    LjBaseResponse appLogin(LoginDto dto, HttpServletRequest request, HttpServletResponse response);

}
