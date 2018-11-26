package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseCFour;

/**
 * Created by Wang on 2018/11/24.
 */
public interface MirrorPhaseCFourService {

    int  insert (MirrorPhaseCFour mirrorPhaseCFour);

    int  update(MirrorPhaseCFour mirrorPhaseCFour);

    MirrorPhaseCFour findByBuId(String buId);
}
