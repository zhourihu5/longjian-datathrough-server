package com.longfor.longjian.datathrough.app.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Wang on 2018/11/23.
 */
@Slf4j
public class DateUtil {

    //日期格式，精确到日 2017-4-16
    public static final DateFormat formatter = DateFormat.getDateInstance();

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

    /**
     * 获取N天前年月日  -1代表获取昨天  1代表获取明天
     * @return
     */
    public static  String getShortDate(int days){

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date date1 = calendar.getTime();
        Long time = date1.getTime();
        return formatter.format(time);
    }


}
