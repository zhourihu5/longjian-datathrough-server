package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.MirrorPhaseDTwoMapper;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseDTwoService;
import com.longfor.longjian.datathrough.po.MirrorPhaseDTwo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/18.
 */
@Service
@Slf4j
public class MirrorPhaseDTwoServiceImpl implements MirrorPhaseDTwoService {

    @Resource
    private MirrorPhaseDTwoMapper mirrorPhaseDTwoMapper;

    /**
     * 插入 D2 分期数据 动态切换到customer01数据源
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int insert(MirrorPhaseDTwo mirrorPhaseCdc) {
        return mirrorPhaseDTwoMapper.insert(mirrorPhaseCdc);
    }

    /**
     * 更新D2分期数据
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int update(MirrorPhaseDTwo mirrorPhaseCdc) {
        return mirrorPhaseDTwoMapper.updateByPrimaryKey(mirrorPhaseCdc);
    }

    /**
     * 根据分期身份证  版本号 获取D2分期信息
     * @param phId
     * @param sapVer
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public MirrorPhaseDTwo findByPhIdSapVer(String phId,String  sapVer) {

        MirrorPhaseDTwo mirrorPhaseDTwo=new MirrorPhaseDTwo();
        mirrorPhaseDTwo.setPhId(phId);
        mirrorPhaseDTwo.setSapVer(sapVer);

        return mirrorPhaseDTwoMapper.selectOne(mirrorPhaseDTwo);
    }


}
