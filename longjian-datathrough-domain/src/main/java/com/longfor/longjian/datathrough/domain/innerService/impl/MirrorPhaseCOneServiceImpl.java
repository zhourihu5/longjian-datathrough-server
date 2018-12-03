package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.MirrorPhaseCOneMapper;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCOneService;
import com.longfor.longjian.datathrough.po.MirrorPhaseCOne;
import com.longfor.longjian.datathrough.po.StageConResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
     * 根据分期身份证  版本号 获取分期信息
     * @param phId
     * @param sapVer
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public MirrorPhaseCOne findByPhIdSapVer(String phId,String  sapVer) {

        MirrorPhaseCOne mirrorPhaseCOne=new MirrorPhaseCOne();
        mirrorPhaseCOne.setPhId(phId);
        mirrorPhaseCOne.setSapVer(sapVer);

        return mirrorPhaseCOneMapper.selectOne(mirrorPhaseCOne);
    }

    /**
     * 获取推送给分期的数据
     * @param updateAt
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public List<StageConResult> getStageOneByUpdateTime(String updateAt) {
        return mirrorPhaseCOneMapper.getStageOneByUpdateTime(updateAt);
    }


}
