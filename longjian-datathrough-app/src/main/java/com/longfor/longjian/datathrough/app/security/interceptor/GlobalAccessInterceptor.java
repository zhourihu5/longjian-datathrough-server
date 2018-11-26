package com.longfor.longjian.datathrough.app.security.interceptor;

import com.google.common.base.Splitter;
import com.longfor.drogon.common.exception.LFBizException;
import com.longfor.drogon.common.util.*;
import com.longfor.longjian.datathrough.consts.LFAccessEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 主数据推送鉴权
 */
@Slf4j
public class GlobalAccessInterceptor extends HandlerInterceptorAdapter {

    @Value("${master.whitelist.enabled}")
    private boolean enabled;
    @Value("${master.whitelist.list}")
    private String whitelist;

    public GlobalAccessInterceptor(){

    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o)
            throws Exception {
            String reqIp = LFIP.getClientRealip(request);
            //验证IP
            if (!checkMurphyWhitelist(reqIp)) {
                throw new LFBizException(LFAccessEnum.E9101.getText());
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


    private boolean checkMurphyWhitelist(String ip) {
        if(enabled){
            List<String> whiteList = Splitter.on(",").trimResults().splitToList(whitelist);
            return whiteList.contains(ip);
        }
        return true;
    }


}

