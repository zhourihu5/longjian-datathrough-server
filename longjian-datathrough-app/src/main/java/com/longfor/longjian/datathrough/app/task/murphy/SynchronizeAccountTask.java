package com.longfor.longjian.datathrough.app.task.murphy;

import com.longfor.longjian.datathrough.domain.innerService.UserLongjianMurphyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/16.
 */
@Slf4j
public class SynchronizeAccountTask {

    @Resource
    private UserLongjianMurphyService userLongjianMurphyService;

    @Scheduled(cron = "0 0/50 * * * ?")
    private void synchronizeAccount(){



    }
}
