package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.MirrorPhaseDThreeMapper;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseDThreeService;
import com.longfor.longjian.datathrough.po.MirrorPhaseDThree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/18.
 */
@Service
@Slf4j
public class MirrorPhaseDThreeServiceImpl implements MirrorPhaseDThreeService {

    @Resource
    private MirrorPhaseDThreeMapper mirrorPhaseDThreeMapper;

    /**
     * 插入 D3 分期数据 动态切换到customer01数据源
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int insert(MirrorPhaseDThree mirrorPhaseCdc) {
        return mirrorPhaseDThreeMapper.insert(mirrorPhaseCdc);
    }

    /**
     * 更新D3分期数据
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int update(MirrorPhaseDThree mirrorPhaseCdc) {
        return mirrorPhaseDThreeMapper.updateByPrimaryKey(mirrorPhaseCdc);
    }

    /**
     * 根据分期身份证  版本号 获取D3分期信息
     * @param phId
     * @param sapVer
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public MirrorPhaseDThree findByPhIdSapVer(String phId,String  sapVer) {

        MirrorPhaseDThree mirrorPhaseDThree=new MirrorPhaseDThree();
        mirrorPhaseDThree.setPhId(phId);
        mirrorPhaseDThree.setSapVer(sapVer);

        return mirrorPhaseDThreeMapper.selectOne(mirrorPhaseDThree);
    }


}
