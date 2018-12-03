package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.MirrorPhaseDOneMapper;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseDOneService;
import com.longfor.longjian.datathrough.po.MirrorPhaseDOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/18.
 */
@Service
@Slf4j
public class MirrorPhaseDOneServiceImpl implements MirrorPhaseDOneService {

    @Resource
    private MirrorPhaseDOneMapper mirrorPhaseDOneMapper;

    /**
     * 插入 d1 分期数据 动态切换到customer01数据源
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int insert(MirrorPhaseDOne mirrorPhaseCdc) {
        return mirrorPhaseDOneMapper.insert(mirrorPhaseCdc);
    }

    /**
     * 更新D1分期数据
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int update(MirrorPhaseDOne mirrorPhaseCdc) {
        return mirrorPhaseDOneMapper.updateByPrimaryKey(mirrorPhaseCdc);
    }

    /**
     * 根据分期身份证  版本号 获取D1分期信息
     * @param phId
     * @param sapVer
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public MirrorPhaseDOne findByPhIdSapVer(String phId,String  sapVer) {

        MirrorPhaseDOne mirrorPhaseDOne=new MirrorPhaseDOne();
        mirrorPhaseDOne.setPhId(phId);
        mirrorPhaseDOne.setSapVer(sapVer);

        return mirrorPhaseDOneMapper.selectOne(mirrorPhaseDOne);
    }


}
