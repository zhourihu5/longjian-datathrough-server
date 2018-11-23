package com.longfor.longjian.datathrough.app.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Created by Wang on 2018/11/23.
 */
@Slf4j
public class DateUtil {
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
