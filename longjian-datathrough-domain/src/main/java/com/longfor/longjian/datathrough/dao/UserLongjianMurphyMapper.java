package com.longfor.longjian.datathrough.dao;

import com.longfor.gaia.gfs.data.mybatis.LFMySQLMapper;
import com.longfor.longjian.datathrough.po.UserLongjianMurphy;

import java.util.Date;

public interface UserLongjianMurphyMapper extends LFMySQLMapper<UserLongjianMurphy> {

    Date getLastSynchronizeTime();

}