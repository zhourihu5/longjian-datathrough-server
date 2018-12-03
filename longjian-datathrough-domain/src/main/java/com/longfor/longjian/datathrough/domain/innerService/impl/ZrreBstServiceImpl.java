package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.ZrreBstMapper;
import com.longfor.longjian.datathrough.domain.innerService.ZrreBstService;
import com.longfor.longjian.datathrough.po.ZrreBst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Wang on 2018/11/29.
 */
@Slf4j
@Service
public class ZrreBstServiceImpl implements ZrreBstService {

    @Resource
    private ZrreBstMapper zrreBstMapper;

    @Override
    @LFAssignDataSource("custom01")
    public int insertList(List<ZrreBst> zrreBstList) {
        return zrreBstMapper.insertList(zrreBstList);
    }
}
