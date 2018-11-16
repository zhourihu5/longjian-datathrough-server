package com.longfor.longjian.datathrough.app.vo;


import lombok.Data;

import java.util.Date;

/**
 * Created by Wang on 2018/11/16.
 */
@Data
public class AccountVo {

    private Long id;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * uid
     */
    private Long uid;

    /**
     * 员工唯一编号
     */
    private String code;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 真实姓名拼音
     */
    private String pinyin;

    /**
     * 真实姓名简拼
     */
    private String shortPinyin;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 组织机构id
     */
    private Long organizationId;

    /**
     * 组织机构完整路径
     */
    private String organizationFullPath;

    /**
     * 关联组织机构id字符串，e.g. 1,2,3,...
     */
    private String likeOrganizationId;

    /**
     * 职位id
     */
    private Long positionId;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 类型：1=管理员，2=一般用户
     */
    private Integer type;

    /**
     * 登录密码最后更新日期
     */
    private Date lastLoginPasswordChangedDate;

    /**
     * 最后登录成功日期
     */
    private Date lastLoginSuccessDate;

    /**
     * 备注
     */
    private String descp;

    /**
     * 状态：0=锁定，1=正常，2=未激活
     */
    private Integer status;

    /**
     * 最后修改人账户
     */
    private String modifiedAccount;

    /**
     * 最后修改人名称
     */
    private String modifiedName;

    private Date createTime;

    private Date modifiedTime;

    private String tenantName;

    private Integer tenantType;
    private Integer sourceType;

    private String tenantBusinessId;

    private String lastSynchronizeTime;
}
