package com.longfor.longjian.datathrough.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mirror_phase_c_four")
public class MirrorPhaseCFour {
    @Id
    private Integer id;

    /**
     * 项目身份证
     */
    @Column(name = "pr_id")
    private String prId;

    /**
     * SAP版本
     */
    private String sapver;

    /**
     * 审批状态
     */
    @Column(name = "ap_status")
    private String apStatus;

    /**
     * 地区公司
     */
    @Column(name = "pr_com")
    private String prCom;

    /**
     * 物业项目名称
     */
    @Column(name = "pr_name")
    private String prName;

    /**
     * 原项目身份证
     */
    @Column(name = "oldpr_id")
    private String oldprId;

    /**
     * 航道编码
     */
    @Column(name = "bu_id")
    private String buId;

    /**
     * 物业项目地址
     */
    @Column(name = "pr_addr")
    private String prAddr;

    /**
     * 所属片区
     */
    @Column(name = "pr_distr")
    private String prDistr;

    /**
     * 创建日期
     */
    @Column(name = "cr_date")
    private Date crDate;

    /**
     * 更新日期
     */
    @Column(name = "ch_date")
    private Date chDate;

    /**
     * 接管时间 
     */
    @Column(name = "take_date")
    private String takeDate;

    /**
     * 物业项目类型
     */
    @Column(name = "pr_type")
    private String prType;

    /**
     * 服务等级
     */
    private String servlev;

    /**
     * 接管类型
     */
    @Column(name = "take_type")
    private String takeType;

    /**
     * 删除标记
     */
    @Column(name = "de_flg")
    private String deFlg;

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
     * 获取项目身份证
     *
     * @return pr_id - 项目身份证
     */
    public String getPrId() {
        return prId;
    }

    /**
     * 设置项目身份证
     *
     * @param prId 项目身份证
     */
    public void setPrId(String prId) {
        this.prId = prId == null ? null : prId.trim();
    }

    /**
     * 获取SAP版本
     *
     * @return sapver - SAP版本
     */
    public String getSapver() {
        return sapver;
    }

    /**
     * 设置SAP版本
     *
     * @param sapver SAP版本
     */
    public void setSapver(String sapver) {
        this.sapver = sapver == null ? null : sapver.trim();
    }

    /**
     * 获取审批状态
     *
     * @return ap_status - 审批状态
     */
    public String getApStatus() {
        return apStatus;
    }

    /**
     * 设置审批状态
     *
     * @param apStatus 审批状态
     */
    public void setApStatus(String apStatus) {
        this.apStatus = apStatus == null ? null : apStatus.trim();
    }

    /**
     * 获取地区公司
     *
     * @return pr_com - 地区公司
     */
    public String getPrCom() {
        return prCom;
    }

    /**
     * 设置地区公司
     *
     * @param prCom 地区公司
     */
    public void setPrCom(String prCom) {
        this.prCom = prCom == null ? null : prCom.trim();
    }

    /**
     * 获取物业项目名称
     *
     * @return pr_name - 物业项目名称
     */
    public String getPrName() {
        return prName;
    }

    /**
     * 设置物业项目名称
     *
     * @param prName 物业项目名称
     */
    public void setPrName(String prName) {
        this.prName = prName == null ? null : prName.trim();
    }

    /**
     * 获取原项目身份证
     *
     * @return oldpr_id - 原项目身份证
     */
    public String getOldprId() {
        return oldprId;
    }

    /**
     * 设置原项目身份证
     *
     * @param oldprId 原项目身份证
     */
    public void setOldprId(String oldprId) {
        this.oldprId = oldprId == null ? null : oldprId.trim();
    }

    /**
     * 获取航道编码
     *
     * @return bu_id - 航道编码
     */
    public String getBuId() {
        return buId;
    }

    /**
     * 设置航道编码
     *
     * @param buId 航道编码
     */
    public void setBuId(String buId) {
        this.buId = buId == null ? null : buId.trim();
    }

    /**
     * 获取物业项目地址
     *
     * @return pr_addr - 物业项目地址
     */
    public String getPrAddr() {
        return prAddr;
    }

    /**
     * 设置物业项目地址
     *
     * @param prAddr 物业项目地址
     */
    public void setPrAddr(String prAddr) {
        this.prAddr = prAddr == null ? null : prAddr.trim();
    }

    /**
     * 获取所属片区
     *
     * @return pr_distr - 所属片区
     */
    public String getPrDistr() {
        return prDistr;
    }

    /**
     * 设置所属片区
     *
     * @param prDistr 所属片区
     */
    public void setPrDistr(String prDistr) {
        this.prDistr = prDistr == null ? null : prDistr.trim();
    }

    /**
     * 获取创建日期
     *
     * @return cr_date - 创建日期
     */
    public Date getCrDate() {
        return crDate;
    }

    /**
     * 设置创建日期
     *
     * @param crDate 创建日期
     */
    public void setCrDate(Date crDate) {
        this.crDate = crDate;
    }

    /**
     * 获取更新日期
     *
     * @return ch_date - 更新日期
     */
    public Date getChDate() {
        return chDate;
    }

    /**
     * 设置更新日期
     *
     * @param chDate 更新日期
     */
    public void setChDate(Date chDate) {
        this.chDate = chDate;
    }

    /**
     * 获取接管时间 
     *
     * @return take_date - 接管时间 
     */
    public String getTakeDate() {
        return takeDate;
    }

    /**
     * 设置接管时间 
     *
     * @param takeDate 接管时间 
     */
    public void setTakeDate(String takeDate) {
        this.takeDate = takeDate == null ? null : takeDate.trim();
    }

    /**
     * 获取物业项目类型
     *
     * @return pr_type - 物业项目类型
     */
    public String getPrType() {
        return prType;
    }

    /**
     * 设置物业项目类型
     *
     * @param prType 物业项目类型
     */
    public void setPrType(String prType) {
        this.prType = prType == null ? null : prType.trim();
    }

    /**
     * 获取服务等级
     *
     * @return servlev - 服务等级
     */
    public String getServlev() {
        return servlev;
    }

    /**
     * 设置服务等级
     *
     * @param servlev 服务等级
     */
    public void setServlev(String servlev) {
        this.servlev = servlev == null ? null : servlev.trim();
    }

    /**
     * 获取接管类型
     *
     * @return take_type - 接管类型
     */
    public String getTakeType() {
        return takeType;
    }

    /**
     * 设置接管类型
     *
     * @param takeType 接管类型
     */
    public void setTakeType(String takeType) {
        this.takeType = takeType == null ? null : takeType.trim();
    }

    /**
     * 获取删除标记
     *
     * @return de_flg - 删除标记
     */
    public String getDeFlg() {
        return deFlg;
    }

    /**
     * 设置删除标记
     *
     * @param deFlg 删除标记
     */
    public void setDeFlg(String deFlg) {
        this.deFlg = deFlg == null ? null : deFlg.trim();
    }
}