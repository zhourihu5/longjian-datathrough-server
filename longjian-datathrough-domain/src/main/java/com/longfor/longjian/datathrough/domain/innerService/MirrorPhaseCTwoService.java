package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseCTwo;
import com.longfor.longjian.datathrough.po.StageConResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Wang on 2018/11/24.
 */
public interface MirrorPhaseCTwoService {

    int  insert (MirrorPhaseCTwo mirrorPhaseCTwo);

    int  update(MirrorPhaseCTwo mirrorPhaseCTwo);

    MirrorPhaseCTwo findByPhIdSapVer(String phId,String sapVer);

    List<StageConResult> getStageTwoByUpdateTime(@Param("updateAt")String updateAt);
}
