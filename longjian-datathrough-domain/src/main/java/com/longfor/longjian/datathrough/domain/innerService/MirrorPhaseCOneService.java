package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseCOne;
import com.longfor.longjian.datathrough.po.StageConResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Wang on 2018/11/18.
 */
public interface MirrorPhaseCOneService {

   int  insert (MirrorPhaseCOne mirrorPhaseCdc);

   int  update(MirrorPhaseCOne mirrorPhaseCdc);

   MirrorPhaseCOne findByPhIdSapVer(String phId,String  sapVer);

   List<StageConResult> getStageOneByUpdateTime(@Param("updateAt")String updateAt);
}
