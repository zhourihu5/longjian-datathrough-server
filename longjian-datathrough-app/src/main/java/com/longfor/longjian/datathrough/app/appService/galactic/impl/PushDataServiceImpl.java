package com.longfor.longjian.datathrough.app.appService.galactic.impl;

import com.longfor.longjian.datathrough.app.appService.galactic.PushDataService;
import com.longfor.longjian.datathrough.app.appService.galactic.PushService;
import com.longfor.longjian.datathrough.app.util.DateUtil;
import com.longfor.longjian.datathrough.app.util.LFResultBean;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCOneService;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCThreeService;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCTwoService;
import com.longfor.longjian.datathrough.po.StageConResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wang on 2018/11/30.
 */
@Slf4j
@Service
public class PushDataServiceImpl implements PushDataService {

    @Resource
    private MirrorPhaseCOneService mirrorPhaseCOneService;

    @Resource
    private MirrorPhaseCTwoService mirrorPhaseCTwoService;

    @Resource
    private MirrorPhaseCThreeService mirrorPhaseCThreeService;

    @Resource
    private PushService pushService;

    @Override
    public LFResultBean pushData(String updateAt) {

        if(!StringUtils.isNotBlank(updateAt)){//如果为空 则默认推送昨天以后的数据
            updateAt= DateUtil.getShortDate(-1)+" 00:00:00";
        }

        List <StageConResult> allList=new ArrayList<>();

        List<StageConResult>stageConResultList1=mirrorPhaseCOneService.getStageOneByUpdateTime(updateAt);//获取C1分期

        List<StageConResult>stageConResultList2=mirrorPhaseCTwoService.getStageTwoByUpdateTime(updateAt);//获取C2分期

        List<StageConResult>stageConResultList3=mirrorPhaseCThreeService.getStageThreeByUpdateTime(updateAt);//获取C3分期

        allList.addAll(stageConResultList1);
        allList.addAll(stageConResultList2);
        allList.addAll(stageConResultList3);

        LFResultBean lfResultBean=pushService.receiveFqMessage(allList);

        return lfResultBean;
    }
}
