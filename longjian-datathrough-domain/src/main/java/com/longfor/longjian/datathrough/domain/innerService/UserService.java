package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.User;

/**
 * Created by Wang on 2018/11/20.
 */
public interface UserService {

    User getUserByMurphyLoginName(String loginName);

    int updateUser(User u);
}
