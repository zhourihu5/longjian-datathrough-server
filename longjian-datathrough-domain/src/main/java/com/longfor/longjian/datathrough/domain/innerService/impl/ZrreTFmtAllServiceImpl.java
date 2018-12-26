package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.ZrreTFmtAllMapper;
import com.longfor.longjian.datathrough.domain.innerService.ZrreTFmtAllService;
import com.longfor.longjian.datathrough.po.ZrreTFmtAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Wang on 2018/11/29.
 */
@Slf4j
@Service
public class ZrreTFmtAllServiceImpl implements ZrreTFmtAllService {

    @Resource
    private ZrreTFmtAllMapper zrreTFmtAllMapper;

    @Override
    @LFAssignDataSource("custom01")
    public int insertList(List<ZrreTFmtAll> zrreTFmtAllList) {
        return zrreTFmtAllMapper.insertList(zrreTFmtAllList);
    }

    @Override
    @LFAssignDataSource("custom01")
    public void deleteAll() {
         zrreTFmtAllMapper.deleteAll();
    }
}
