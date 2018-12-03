package com.longfor.longjian.datathrough.dao;

import com.longfor.gaia.gfs.data.mybatis.LFMySQLMapper;
import com.longfor.longjian.datathrough.po.MirrorPhaseCTwo;
import com.longfor.longjian.datathrough.po.StageConResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MirrorPhaseCTwoMapper extends LFMySQLMapper<MirrorPhaseCTwo> {

    List<StageConResult> getStageTwoByUpdateTime(@Param("updateAt")String updateAt);
}