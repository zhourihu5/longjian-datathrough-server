package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.MirrorPhaseCThreeMapper;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCThreeService;
import com.longfor.longjian.datathrough.po.MirrorPhaseCThree;
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
public class MirrorPhaseCThreeServiceImpl implements MirrorPhaseCThreeService {

    @Resource
    private MirrorPhaseCThreeMapper mirrorPhaseCThreeMapper;

    /**
     * 插入 c3 分期数据 动态切换到customer01数据源
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int insert(MirrorPhaseCThree mirrorPhaseCdc) {
        return mirrorPhaseCThreeMapper.insert(mirrorPhaseCdc);
    }

    /**
     * 更新C3分期数据
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int update(MirrorPhaseCThree mirrorPhaseCdc) {
        return mirrorPhaseCThreeMapper.updateByPrimaryKey(mirrorPhaseCdc);
    }

    /**
     * 根据分期身份证 版本获取分期信息
     * @param phId
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public MirrorPhaseCThree findByPhIdSapVer(String phId,String sapVer) {

        MirrorPhaseCThree mirrorPhaseCThree=new MirrorPhaseCThree();
        mirrorPhaseCThree.setPhId(phId);
        mirrorPhaseCThree.setSapVer(sapVer);

        return mirrorPhaseCThreeMapper.selectOne(mirrorPhaseCThree);
    }

    @Override
    @LFAssignDataSource("custom01")
    public List<StageConResult> getStageThreeByUpdateTime(String updateAt) {
        return mirrorPhaseCThreeMapper.getStageThreeByUpdateTime(updateAt);
    }


}
