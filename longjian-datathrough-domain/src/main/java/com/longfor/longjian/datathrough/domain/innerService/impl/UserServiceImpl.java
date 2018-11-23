package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.longjian.datathrough.dao.UserMapper;
import com.longfor.longjian.datathrough.domain.innerService.UserService;
import com.longfor.longjian.datathrough.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/20.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByMurphyLoginName(String loginName) {
        return userMapper.getUserByMurphyLoginName(loginName);
    }

    @Override
    public int updateUser(User u) {
        return userMapper.updateUser(u);
    }
}
