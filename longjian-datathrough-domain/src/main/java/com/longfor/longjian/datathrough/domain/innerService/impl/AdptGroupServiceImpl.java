package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.AdptGroupMapper;
import com.longfor.longjian.datathrough.domain.innerService.AdptGroupService;
import com.longfor.longjian.datathrough.po.AdptGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Wang on 2018/11/28.
 */
@Slf4j
@Service
public class AdptGroupServiceImpl implements AdptGroupService {

    @Resource
    private AdptGroupMapper adptGroupMapper;

    /**
     * 批量插入组团数据
     * @param adptGroupList
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int insertList(List<AdptGroup> adptGroupList) {
        return adptGroupMapper.insertList(adptGroupList);
    }

    /**
     * 根据分期身份证删除 组团数据
     * @param phId
     * @return
     */
    @Override
    @LFAssignDataSource("custom01")
    public int delByPhId(String phId) {
        AdptGroup adptGroup=new AdptGroup();
        adptGroup.setPhId(phId);
        return adptGroupMapper.delete(adptGroup);
    }
}
