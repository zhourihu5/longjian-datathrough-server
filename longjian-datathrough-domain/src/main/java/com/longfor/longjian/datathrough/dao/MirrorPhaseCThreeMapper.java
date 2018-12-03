package com.longfor.longjian.datathrough.dao;

import com.longfor.gaia.gfs.data.mybatis.LFMySQLMapper;
import com.longfor.longjian.datathrough.po.MirrorPhaseCThree;
import com.longfor.longjian.datathrough.po.StageConResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MirrorPhaseCThreeMapper extends LFMySQLMapper<MirrorPhaseCThree> {
    List<StageConResult> getStageThreeByUpdateTime(@Param("updateAt")String updateAt);
}