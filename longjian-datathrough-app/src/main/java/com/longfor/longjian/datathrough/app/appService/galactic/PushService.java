package com.longfor.longjian.datathrough.app.appService.galactic;

import com.longfor.gaia.gfs.web.feign.LFFeignClient;
import com.longfor.gaia.gfs.web.feign.config.LFFeignConfiguration;
import com.longfor.longjian.datathrough.app.util.LFResultBean;
import com.longfor.longjian.datathrough.po.StageConResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by Wang on 2018/11/30.
 */
@LFFeignClient(group = "galactic", value ="push-stage", configuration = LFFeignConfiguration.class)
public interface PushService {
    /**
     *  调用文档接口 推送数据
     * @param
     * @return
     */
    @PostMapping(value="receiveFqMessage",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    LFResultBean receiveFqMessage(
            @RequestBody List<StageConResult> stageConResultList);

}
