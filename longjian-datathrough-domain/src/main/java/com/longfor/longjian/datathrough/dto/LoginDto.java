package com.longfor.longjian.datathrough.dto;

import lombok.Data;

/**
 * Created by Wang on 2018/11/18.
 */
@Data
public class LoginDto {
    /**
     * 登录账号
     */
    private String user_name;

    /**
     * 明文密码
     */
    private String password;

    /**
     *
     */
    private String group_code;

    /**
     * 是否记住我
     */
    private int remember_me;

    /**
     * 上个接口返回的key
     */
    private long additionalCheckKey;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 身份证号
     */
    private String credentials_no;

    /**
     * 所选租户id
     */
    private long selectTenantId;

    /**
     * 调用墨菲接口类型  1 是baseMurphy  2是baseMurphyAdditionalCheck 3是baseMurphySelectTenant
     */
    private int loginType;
}
