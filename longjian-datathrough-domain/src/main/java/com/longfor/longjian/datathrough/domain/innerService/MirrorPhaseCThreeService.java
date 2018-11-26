package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseCThree;

/**
 * Created by Wang on 2018/11/24.
 */
public interface MirrorPhaseCThreeService {

    int  insert (MirrorPhaseCThree mirrorPhaseCThree);

    int  update(MirrorPhaseCThree mirrorPhaseCThree);

    MirrorPhaseCThree findByBuId(String buId);
}
