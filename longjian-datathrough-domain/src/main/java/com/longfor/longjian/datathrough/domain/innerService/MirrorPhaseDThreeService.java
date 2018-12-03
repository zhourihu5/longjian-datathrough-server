package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseDThree;

/**
 * Created by Wang on 2018/11/18.
 */
public interface MirrorPhaseDThreeService {

   int  insert(MirrorPhaseDThree mirrorPhaseDThree);

   int  update(MirrorPhaseDThree mirrorPhaseDThree);

   MirrorPhaseDThree findByPhIdSapVer(String phId, String sapVer);
}
