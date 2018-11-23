package com.longfor.longjian.datathrough.dto;

import lombok.Data;

/**
 * 墨菲登录接口一参数
 * Created by Wang on 2018/11/23.
 */
@Data
public class LoginMurphyOneDto {
    private String loginName;
    private String encryptPassword;
    private String projectKey;
    private int expandDataType;
    private int rsaType;
    private long tenantTriggerId;
}
