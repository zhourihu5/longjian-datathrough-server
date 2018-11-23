package com.longfor.longjian.datathrough.app.controller;

import com.longfor.longjian.common.base.LjBaseResponse;
import com.longfor.longjian.datathrough.app.appService.master.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

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
     * @param systemId
     * @param data
     * @return
     */
    @PostMapping(value = "excuteProject", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LjBaseResponse excuteProject(@PathParam("SystemID") String systemId, @RequestBody String data){
        masterService.excuteProject(systemId,data);
        return new LjBaseResponse();
    }
}
