package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseCTwo;

/**
 * Created by Wang on 2018/11/24.
 */
public interface MirrorPhaseCTwoService {

    int  insert (MirrorPhaseCTwo mirrorPhaseCTwo);

    int  update(MirrorPhaseCTwo mirrorPhaseCTwo);

    MirrorPhaseCTwo findByBuId(String buId);
}
