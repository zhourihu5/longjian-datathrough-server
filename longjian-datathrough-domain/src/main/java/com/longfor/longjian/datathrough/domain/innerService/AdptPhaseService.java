package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.AdptPhase;

/**
 * Created by Wang on 2018/11/28.
 */
public interface AdptPhaseService {
    int insert(AdptPhase adptPhase);
    int update(AdptPhase adptPhase);
    AdptPhase selectByFqXmCode(String fqCode,String xmCode);
}
