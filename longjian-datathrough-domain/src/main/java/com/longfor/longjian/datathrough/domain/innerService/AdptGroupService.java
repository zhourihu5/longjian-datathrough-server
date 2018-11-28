package com.longfor.longjian.datathrough.domain.innerService;

import com.longfor.longjian.datathrough.po.AdptGroup;

import java.util.List;

/**
 * Created by Wang on 2018/11/28.
 */
public interface AdptGroupService {

    int insertList(List<AdptGroup> adptGroupList);

    int delByPhId(String phId);
}
