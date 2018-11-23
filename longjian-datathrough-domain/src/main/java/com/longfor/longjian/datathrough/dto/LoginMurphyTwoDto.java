package com.longfor.longjian.datathrough.dto;

import lombok.Data;

/**
 * 墨菲登录接口二参数
 * Created by Wang on 2018/11/23.
 */
@Data
public class LoginMurphyTwoDto {

    private long additionalCheckKey;

    private String phone;

    private String email;

    private String credentials_no;

}
