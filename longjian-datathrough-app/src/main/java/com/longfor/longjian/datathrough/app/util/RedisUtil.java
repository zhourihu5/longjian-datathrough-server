package com.longfor.longjian.datathrough.app.util;

import com.alibaba.fastjson.JSON;
import com.longfor.longjian.datathrough.domain.innerService.RelLhCompanyToCompanyService;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Wang on 2018/12/11.
 */
public class RedisUtil {


    /**
     * 获取公司映射关系数据
     * @return
     */
    public static Map<String,Object> getRelLhCompanyToCompanyMap(RelLhCompanyToCompanyService relLhCompanyToCompanyService, RedisTemplate dataThroughRedis){
        //从内存中查询公司映射关系信息
        Map<String,Object>data=new HashMap<>();
        if (!dataThroughRedis.hasKey("master_company_data")){
            data = relLhCompanyToCompanyService.getRelLhCompanyToCompanyMap();

            dataThroughRedis.opsForValue().set("master_company_data", JSON.toJSONString(data),24, TimeUnit.HOURS);
        }else{
            String companyData=dataThroughRedis.opsForValue().get("master_company_data")+"";
            data=JSON.parseObject(companyData, Map.class);
        }
        return data;
    }
}
