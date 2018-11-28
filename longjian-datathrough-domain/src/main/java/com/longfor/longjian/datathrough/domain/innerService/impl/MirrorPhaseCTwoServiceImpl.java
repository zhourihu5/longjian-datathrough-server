package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.MirrorPhaseCTwoMapper;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCTwoService;
import com.longfor.longjian.datathrough.po.MirrorPhaseCTwo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/18.
 */
@Service
@Slf4j
public class MirrorPhaseCTwoServiceImpl implements MirrorPhaseCTwoService {

    @Resource
    private MirrorPhaseCTwoMapper mirrorPhaseCTwoMapper;

    /**
     * 插入 c2 分期数据 动态切换到customer01数据源
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int insert(MirrorPhaseCTwo mirrorPhaseCdc) {
        return mirrorPhaseCTwoMapper.insert(mirrorPhaseCdc);
    }

    /**
     * 更新C2分期数据
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int update(MirrorPhaseCTwo mirrorPhaseCdc) {
        return mirrorPhaseCTwoMapper.updateByPrimaryKey(mirrorPhaseCdc);
    }

    /**
     * 根据分期身份证 版本获取分期信息
     * @param phId
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public MirrorPhaseCTwo findByPhIdSapVer(String phId,String sapVer) {

        MirrorPhaseCTwo mirrorPhaseCTwo=new MirrorPhaseCTwo();
        mirrorPhaseCTwo.setPhId(phId);
        mirrorPhaseCTwo.setSapVer(sapVer);

        return mirrorPhaseCTwoMapper.selectOne(mirrorPhaseCTwo);
    }


}
