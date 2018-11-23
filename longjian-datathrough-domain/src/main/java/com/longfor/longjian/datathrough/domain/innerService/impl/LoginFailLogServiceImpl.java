package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.longjian.datathrough.dao.LoginFailLogMapper;
import com.longfor.longjian.datathrough.domain.innerService.LoginFailLogService;
import com.longfor.longjian.datathrough.po.LoginFailLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/19.
 */
@Slf4j
@Service
public class LoginFailLogServiceImpl implements LoginFailLogService{

    @Resource
    private LoginFailLogMapper loginFailLogMapper;

    /**
     * 插入登录失败日志
     * @param loginFailLog
     * @return
     */
    @Override
    public int insert(LoginFailLog loginFailLog) {
        return loginFailLogMapper.insert(loginFailLog);
    }
}
