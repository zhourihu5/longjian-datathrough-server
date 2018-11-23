package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.longjian.datathrough.dao.LoginSuccessLogMapper;
import com.longfor.longjian.datathrough.domain.innerService.LoginSuccessLogService;
import com.longfor.longjian.datathrough.po.LoginSuccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/19.
 */
@Service
@Slf4j
public class LoginSuccessLogServiceImpl implements LoginSuccessLogService{

    @Resource
    private LoginSuccessLogMapper loginSuccessLogMapper;

    /**
     * 插入登录成功日志
     * @param loginSuccessLog
     * @return
     */
    @Override
    public int insert(LoginSuccessLog loginSuccessLog) {
        return loginSuccessLogMapper.insert(loginSuccessLog);
    }
}
