package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.MirrorPhaseCOneMapper;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCOneService;
import com.longfor.longjian.datathrough.po.MirrorPhaseCOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/18.
 */
@Service
@Slf4j
public class MirrorPhaseCOneServiceImpl implements MirrorPhaseCOneService {

    @Resource
    private MirrorPhaseCOneMapper mirrorPhaseCOneMapper;

    /**
     * 插入 c1 分期数据 动态切换到customer01数据源
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int insert(MirrorPhaseCOne mirrorPhaseCdc) {
        return mirrorPhaseCOneMapper.insert(mirrorPhaseCdc);
    }

    /**
     * 更新C1分期数据
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int update(MirrorPhaseCOne mirrorPhaseCdc) {
        return mirrorPhaseCOneMapper.updateByPrimaryKey(mirrorPhaseCdc);
    }

    /**
     * 根据分期身份证获取分期信息
     * @param phId
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public MirrorPhaseCOne findByBuId(String phId) {

        MirrorPhaseCOne mirrorPhaseCdc=new MirrorPhaseCOne();
        mirrorPhaseCdc.setPhId(phId);

        return mirrorPhaseCOneMapper.selectOne(mirrorPhaseCdc);
    }


}
