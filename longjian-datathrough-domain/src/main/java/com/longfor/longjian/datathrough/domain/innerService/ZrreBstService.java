package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.ZrreBst;

import java.util.List;

/**
 * Created by Wang on 2018/11/29.
 */
public interface ZrreBstService {

    int insertList(List<ZrreBst> zrreBstList);
    void deleteAll();
}
