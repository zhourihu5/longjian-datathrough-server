package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.AdptProjMapper;
import com.longfor.longjian.datathrough.domain.innerService.AdptProjService;
import com.longfor.longjian.datathrough.po.AdptProj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/18.
 */
@Service
@Slf4j
public class AdptProjServiceImpl implements AdptProjService {

    @Resource
    private AdptProjMapper adptProjMapper;

    /**
     * 根据项目code获取 项目信息
     * @param PrCode
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public AdptProj getByPrCode(String PrCode) {
        AdptProj adptProj=new AdptProj();
        adptProj.setLhXmcode(PrCode);
        return adptProjMapper.selectOne(adptProj);
    }

    @Override
    @LFAssignDataSource("custom01")
    public int updateAdptProj(AdptProj adptProj) {
        return adptProjMapper.updateByPrimaryKey(adptProj);
    }

    @Override
    @LFAssignDataSource("custom01")
    public int createAdptProj(AdptProj adptProj) {
        return adptProjMapper.insertSelective(adptProj);
    }
}
