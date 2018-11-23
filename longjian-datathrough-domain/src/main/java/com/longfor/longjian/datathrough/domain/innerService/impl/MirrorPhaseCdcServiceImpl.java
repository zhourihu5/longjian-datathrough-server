package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.MirrorPhaseCdcMapper;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCdcService;
import com.longfor.longjian.datathrough.po.MirrorPhaseCdc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/18.
 */
@Service
@Slf4j
public class MirrorPhaseCdcServiceImpl implements MirrorPhaseCdcService {

    @Resource
    private MirrorPhaseCdcMapper mirrorPhaseCdcMapper;

    /**
     * 插入 c1 分期数据 动态切换到customer01数据源
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int insert(MirrorPhaseCdc mirrorPhaseCdc) {
        return mirrorPhaseCdcMapper.insert(mirrorPhaseCdc);
    }

    /**
     * 更新C1分期数据
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int update(MirrorPhaseCdc mirrorPhaseCdc) {
        return mirrorPhaseCdcMapper.updateByPrimaryKey(mirrorPhaseCdc);
    }

    /**
     * 根据分期身份证获取分期信息
     * @param buId
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public MirrorPhaseCdc findByBuId(String buId) {

        MirrorPhaseCdc mirrorPhaseCdc=new MirrorPhaseCdc();
        mirrorPhaseCdc.setBuId(buId);

        return mirrorPhaseCdcMapper.selectOne(mirrorPhaseCdc);
    }


}
