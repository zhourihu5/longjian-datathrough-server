package com.longfor.longjian.datathrough.po;

import tk.mybatis.mapper.annotation.KeySql;

import java.util.Date;
import javax.persistence.*;

@Table(name = "rel_lh_company_to_company")
public class RelLhCompanyToCompany {
    @Id
    @Column(name = "auto_id")
    @KeySql(useGeneratedKeys = true)
    private Integer autoId;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "lh_gscode")
    private String lhGscode;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "delete_at")
    private Date deleteAt;

    /**
     * @return auto_id
     */
    public Integer getAutoId() {
        return autoId;
    }

    /**
     * @param autoId
     */
    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    /**
     * @return group_id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * @return company_id
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * @return lh_gscode
     */
    public String getLhGscode() {
        return lhGscode;
    }

    /**
     * @param lhGscode
     */
    public void setLhGscode(String lhGscode) {
        this.lhGscode = lhGscode == null ? null : lhGscode.trim();
    }

    /**
     * @return create_at
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * @param createAt
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * @return delete_at
     */
    public Date getDeleteAt() {
        return deleteAt;
    }

    /**
     * @param deleteAt
     */
    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }
}