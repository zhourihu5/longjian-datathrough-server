package com.longfor.longjian.datathrough.dao;

import com.longfor.gaia.gfs.data.mybatis.LFMySQLMapper;
import com.longfor.longjian.datathrough.po.RelLhCompanyToCompany;

import java.util.List;
import java.util.Map;

public interface RelLhCompanyToCompanyMapper extends LFMySQLMapper<RelLhCompanyToCompany> {

    List<Map<String,Object>> getComapnyMapData();
}