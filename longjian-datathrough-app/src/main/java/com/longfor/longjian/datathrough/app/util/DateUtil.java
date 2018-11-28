package com.longfor.longjian.datathrough.app.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by Wang on 2018/11/23.
 */
@Slf4j
public class DateUtil {

    /**
     * 时间戳转化成date
     * @param ts
     * @return
     */
    public static Date stampToDate(String  ts){
        if(!StringUtils.isNotBlank(ts)){
            return null;
        }
        long lt = new Long(ts);
        Date date=new Date(lt);
        return date;
    }

    /*
   * 将时间转换为时间戳
   */
    public static long dateToStamp(Date date) {
        long ts;
        if(date==null){
            ts=0;
        }
         ts = date.getTime();
        return ts;
    }

}
