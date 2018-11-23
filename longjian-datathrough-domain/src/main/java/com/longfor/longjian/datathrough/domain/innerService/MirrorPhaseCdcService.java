package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseCdc;

/**
 * Created by Wang on 2018/11/18.
 */
public interface MirrorPhaseCdcService {

   int  insert (MirrorPhaseCdc mirrorPhaseCdc);

   int  update(MirrorPhaseCdc mirrorPhaseCdc);

   MirrorPhaseCdc findByBuId(String buId);
}
