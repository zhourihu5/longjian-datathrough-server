package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.longjian.datathrough.dao.UserLongjianMurphyMapper;
import com.longfor.longjian.datathrough.domain.innerService.UserLongjianMurphyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Wang on 2018/11/16.
 */
@Service
@Slf4j
public class UserLongjianMurphyServiceImpl implements UserLongjianMurphyService {

    @Resource
    private UserLongjianMurphyMapper userLongjianMurphyMapper;


    /**
     * 获取上一次同步时间
     * @return
     */
    @Override
    public Date getLastSynchronizeTime() {
        return userLongjianMurphyMapper.getLastSynchronizeTime();
    }
}
