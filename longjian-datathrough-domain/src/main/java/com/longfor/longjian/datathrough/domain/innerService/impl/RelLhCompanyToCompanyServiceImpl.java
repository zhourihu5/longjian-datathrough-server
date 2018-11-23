package com.longfor.longjian.datathrough.domain.innerService.impl;

import com.longfor.gaia.gfs.data.mybatis.datasource.LFAssignDataSource;
import com.longfor.longjian.datathrough.dao.RelLhCompanyToCompanyMapper;
import com.longfor.longjian.datathrough.domain.innerService.RelLhCompanyToCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang on 2018/11/18.
 */
@Service
@Slf4j
public class RelLhCompanyToCompanyServiceImpl implements RelLhCompanyToCompanyService {

    @Resource
    private RelLhCompanyToCompanyMapper relLhCompanyToCompanyMapper;

    @Override
    @LFAssignDataSource("custom01")
    public Map<String, Object> getRelLhCompanyToCompanyMap() {
        List<Map<String, Object>> regionMap = relLhCompanyToCompanyMapper.getComapnyMapData();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (Map<String, Object> map : regionMap) {
            String region = null;
            Object id = null;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if ("gscode".equals(entry.getKey())) {
                    region = (String) entry.getValue();
                } else if ("companyId".equals(entry.getKey())) {
                    id = entry.getValue();
                }
            }
            resultMap.put(region, id);
        }
        return resultMap;
    }
}
