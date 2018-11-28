package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.AdptPhaseMapper;
import com.longfor.longjian.datathrough.domain.innerService.AdptPhaseService;
import com.longfor.longjian.datathrough.po.AdptPhase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/28.
 */
@Slf4j
@Service
public class AdptPhaseServiceImpl implements AdptPhaseService {

    @Resource
    private AdptPhaseMapper adptPhaseMapper;

    @Override
    @LFAssignDataSource("custom01")
    public int insert(AdptPhase adptPhase) {
        return adptPhaseMapper.insert(adptPhase);
    }

    @Override
    @LFAssignDataSource("custom01")
    public int update(AdptPhase adptPhase) {
        return adptPhaseMapper.updateByPrimaryKey(adptPhase);
    }

    @Override
    @LFAssignDataSource("custom01")
    public AdptPhase selectByFqXmCode(String fqCode, String xmCode) {
        AdptPhase adptPhase=new AdptPhase();
        adptPhase.setLhFqcode(fqCode);
        adptPhase.setLhXmcode(xmCode);
        return adptPhaseMapper.selectOne(adptPhase);
    }
}
