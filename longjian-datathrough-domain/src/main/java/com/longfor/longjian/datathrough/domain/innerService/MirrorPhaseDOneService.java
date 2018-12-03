package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.MirrorPhaseDOne;

/**
 * Created by Wang on 2018/11/18.
 */
public interface MirrorPhaseDOneService {

   int  insert(MirrorPhaseDOne mirrorPhaseDOne);

   int  update(MirrorPhaseDOne mirrorPhaseDOne);

   MirrorPhaseDOne findByPhIdSapVer(String phId, String sapVer);
}
