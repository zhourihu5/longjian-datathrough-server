package com.longfor.longjian.datathrough.app.controller;

import com.longfor.longjian.common.base.LjBaseResponse;
import com.longfor.longjian.datathrough.app.appService.galactic.PushDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/30.
 */
@RestController
@Slf4j
@RequestMapping("/stage/galactic")
public class StageGalacticController {


    @Resource
    private PushDataService pushDataService;

    /**
     * 往 文档推送分期信息
     * @param updateAt
     * @return
     */
    @RequestMapping(value = "push",method = RequestMethod.POST)
    public LjBaseResponse push(@RequestParam("updateAt")String updateAt){
        pushDataService.pushData(updateAt);
        return new LjBaseResponse();
    }
}
