package com.longfor.longjian.datathrough.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_longjian_murphy")
public class UserLongjianMurphy {
    @Id
    private Integer id;

    /**
     * 龙建用户id
     */
    @Column(name = "longjian_user_id")
    private Integer longjianUserId;

    /**
     * 墨菲登录账号
     */
    @Column(name = "murphy_login_name")
    private String murphyLoginName;

    /**
     * 租户id
     */
    @Column(name = "tenant_id")
    private Long tenantId;

    /**
     * 上一次同步时间
     */
    @Column(name = "last_synchronize_time")
    private Date lastSynchronizeTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取龙建用户id
     *
     * @return longjian_user_id - 龙建用户id
     */
    public Integer getLongjianUserId() {
        return longjianUserId;
    }

    /**
     * 设置龙建用户id
     *
     * @param longjianUserId 龙建用户id
     */
    public void setLongjianUserId(Integer longjianUserId) {
        this.longjianUserId = longjianUserId;
    }

    /**
     * 获取墨菲登录账号
     *
     * @return murphy_login_name - 墨菲登录账号
     */
    public String getMurphyLoginName() {
        return murphyLoginName;
    }

    /**
     * 设置墨菲登录账号
     *
     * @param murphyLoginName 墨菲登录账号
     */
    public void setMurphyLoginName(String murphyLoginName) {
        this.murphyLoginName = murphyLoginName == null ? null : murphyLoginName.trim();
    }

    /**
     * 获取租户id
     *
     * @return tenant_id - 租户id
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * 设置租户id
     *
     * @param tenantId 租户id
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取上一次同步时间
     *
     * @return last_synchronize_time - 上一次同步时间
     */
    public Date getLastSynchronizeTime() {
        return lastSynchronizeTime;
    }

    /**
     * 设置上一次同步时间
     *
     * @param lastSynchronizeTime 上一次同步时间
     */
    public void setLastSynchronizeTime(Date lastSynchronizeTime) {
        this.lastSynchronizeTime = lastSynchronizeTime;
    }
}