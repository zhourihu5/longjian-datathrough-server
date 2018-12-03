package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseCThree;
import com.longfor.longjian.datathrough.po.StageConResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Wang on 2018/11/24.
 */
public interface MirrorPhaseCThreeService {

    int  insert (MirrorPhaseCThree mirrorPhaseCThree);

    int  update(MirrorPhaseCThree mirrorPhaseCThree);

    MirrorPhaseCThree findByPhIdSapVer(String phId,String sapVer);

    List<StageConResult> getStageThreeByUpdateTime(@Param("updateAt")String updateAt);
}
