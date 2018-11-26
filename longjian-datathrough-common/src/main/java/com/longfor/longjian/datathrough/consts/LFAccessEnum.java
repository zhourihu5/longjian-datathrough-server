package com.longfor.longjian.datathrough.consts;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by Wang on 2018/11/26.
 */
public enum LFAccessEnum {

    E9101("E1001", "[E1001]请求地址不在白名单内，请求失败");

    public final String code;
    public final String text;

    private LFAccessEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getText(String... values) {
        String msg = this.text;
        String[] var3 = values;
        int var4 = values.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String val = var3[var5];
            msg = msg.replaceFirst("\\{\\?\\}", val);
        }

        return msg;
    }

    @JsonCreator
    public static LFAccessEnum of(String code) {
        LFAccessEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            LFAccessEnum commonEnum = var1[var3];
            if(code.equals(commonEnum.code)) {
                return commonEnum;
            }
        }

        return null;
    }


}
