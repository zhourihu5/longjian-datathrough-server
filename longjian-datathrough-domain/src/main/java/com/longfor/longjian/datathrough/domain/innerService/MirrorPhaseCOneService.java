package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseCOne;

/**
 * Created by Wang on 2018/11/18.
 */
public interface MirrorPhaseCOneService {

   int  insert (MirrorPhaseCOne mirrorPhaseCdc);

   int  update(MirrorPhaseCOne mirrorPhaseCdc);

   MirrorPhaseCOne findByPhIdSapVer(String phId,String  sapVer);
}
