package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.MirrorPhaseCFourMapper;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCFourService;
import com.longfor.longjian.datathrough.po.MirrorPhaseCFour;
import com.longfor.longjian.datathrough.po.MirrorPhaseCOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/18.
 */
@Service
@Slf4j
public class MirrorPhaseCFourServiceImpl implements MirrorPhaseCFourService {

    @Resource
    private MirrorPhaseCFourMapper mirrorPhaseCFourMapper;

    /**
     * 插入 c4 数据 动态切换到customer01数据源
     * @param mirrorPhaseCFour
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int insert(MirrorPhaseCFour mirrorPhaseCFour) {
        return mirrorPhaseCFourMapper.insert(mirrorPhaseCFour);
    }

    /**
     * 更新C4数据
     * @param mirrorPhaseCdc
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int update(MirrorPhaseCFour mirrorPhaseCdc) {
        return mirrorPhaseCFourMapper.updateByPrimaryKey(mirrorPhaseCdc);
    }

    /**
     * 根据项目身份证 版本获取信息
     * @param prId
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public MirrorPhaseCFour findByPhIdSapVer(String prId,String sapVer) {

        MirrorPhaseCFour mirrorPhaseCFour=new MirrorPhaseCFour();
        mirrorPhaseCFour.setPrId(prId);
        mirrorPhaseCFour.setSapver(sapVer);

        return mirrorPhaseCFourMapper.selectOne(mirrorPhaseCFour);
    }

}
