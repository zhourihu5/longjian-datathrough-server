package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseDTwo;

/**
 * Created by Wang on 2018/11/18.
 */
public interface MirrorPhaseDTwoService {

   int  insert(MirrorPhaseDTwo mirrorPhaseDTwo);

   int  update(MirrorPhaseDTwo mirrorPhaseDTwo);

   MirrorPhaseDTwo findByPhIdSapVer(String phId, String sapVer);
}
