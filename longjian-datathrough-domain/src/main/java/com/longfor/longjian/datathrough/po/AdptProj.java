package com.longfor.longjian.datathrough.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "adpt_proj")
public class AdptProj {
    @Id
    @Column(name = "auto_id")
    private Integer autoId;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "lh_xmcode")
    private String lhXmcode;

    @Column(name = "lh_xmname")
    private String lhXmname;

    @Column(name = "lh_gscode")
    private String lhGscode;

    @Column(name = "menu_type")
    private String menuType;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

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
     * @return lh_xmcode
     */
    public String getLhXmcode() {
        return lhXmcode;
    }

    /**
     * @param lhXmcode
     */
    public void setLhXmcode(String lhXmcode) {
        this.lhXmcode = lhXmcode == null ? null : lhXmcode.trim();
    }

    /**
     * @return lh_xmname
     */
    public String getLhXmname() {
        return lhXmname;
    }

    /**
     * @param lhXmname
     */
    public void setLhXmname(String lhXmname) {
        this.lhXmname = lhXmname == null ? null : lhXmname.trim();
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
     * @return menu_type
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * @param menuType
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType == null ? null : menuType.trim();
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
     * @return update_at
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * @param updateAt
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
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