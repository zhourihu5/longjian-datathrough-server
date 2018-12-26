package com.longfor.longjian.datathrough.app.task;

import com.longfor.longjian.datathrough.app.appService.galactic.PushDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * Created by Wang on 2018/12/11.
 */
@Slf4j
@Component
public class StageGalacticTask {

    @Value("${longfor.web.feign.clients.galactic.push-stage.isPush}")
    private String isPush;

    @Resource
    private PushDataService pushDataService;

    @Scheduled(cron = "0 10 0 * * ?")
    public void pushStageToGalactic() throws ParseException {

        if("true".equals(isPush)){//如果确认推送消息

            pushDataService.pushData("");

       }
    }
}
