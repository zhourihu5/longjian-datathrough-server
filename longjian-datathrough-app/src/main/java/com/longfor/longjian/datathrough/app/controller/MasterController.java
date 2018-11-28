package com.longfor.longjian.datathrough.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.longfor.longjian.common.base.LjBaseResponse;
import com.longfor.longjian.datathrough.app.appService.master.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/17.
 */
@RestController
@Slf4j
@RequestMapping("master/data/")
public class MasterController {
    @Resource
    private MasterService masterService;


    /**
     * 主数据 接收项目信息
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "excuteProject",method = RequestMethod.POST)
    public LjBaseResponse excuteProject(@RequestBody JSONObject jsonObject){
        masterService.excuteProject(jsonObject);
        return new LjBaseResponse();
    }


    /**
     * 主数据 接收项目信息
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "excuteStage",method = RequestMethod.POST)
    public LjBaseResponse excuteStage(@RequestBody JSONObject jsonObject){
        masterService.excuteStage(jsonObject);
        return new LjBaseResponse();
    }
}
