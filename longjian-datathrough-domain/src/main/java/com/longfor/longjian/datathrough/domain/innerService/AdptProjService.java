package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.AdptProj;

/**
 * Created by Wang on 2018/11/18.
 */
public interface AdptProjService {

    AdptProj getByPrCode(String PrCode);
    int updateAdptProj(AdptProj adptProj);

    int createAdptProj(AdptProj adptProj);
}
