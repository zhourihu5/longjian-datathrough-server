package com.longfor.longjian.datathrough.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mirror_phase_c_one")
public class MirrorPhaseCOne {
    @Id
    private Integer id;

    /**
     * 分期身份证
     */
    @Column(name = "ph_id")
    private String phId;

    /**
     * SAP版本
     */
    @Column(name = "sap_ver")
    private String sapVer;

    /**
     * 版本名称
     */
    @Column(name = "ver_nam")
    private String verNam;

    /**
     * 历史编码
     */
    @Column(name = "his_code")
    private String hisCode;

    /**
     * 历史身份证
     */
    @Column(name = "his_icard")
    private String hisIcard;

    /**
     * 历史id
     */
    @Column(name = "his_guid")
    private String hisGuid;

    /**
     * 历史标识
     */
    @Column(name = "his_flg")
    private String hisFlg;

    /**
     * 分期名称
     */
    @Column(name = "ph_name")
    private String phName;

    /**
     * 审批状态
     */
    @Column(name = "ap_status")
    private String apStatus;

    /**
     * 删除标记
     */
    @Column(name = "de_flg")
    private String deFlg;

    /**
     * 分期案名
     */
    @Column(name = "ph_cname")
    private String phCname;

    /**
     * 项目公司
     */
    @Column(name = "pr_compan")
    private String prCompan;

    /**
     * 航道编码
     */
    @Column(name = "bu_id")
    private String buId;

    /**
     * 产品定档
     */
    @Column(name = "ph_prdlev")
    private String phPrdlev;

    /**
     * 研发管控等级
     */
    @Column(name = "ph_devlev")
    private String phDevlev;

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
     * 项目身份证
     */
    @Column(name = "pr_id")
    private String prId;

    /**
     * 操盘类型
     */
    @Column(name = "ph_optyp")
    private String phOptyp;

    /**
     * 当前权益比例
     */
    @Column(name = "ph_equi_r")
    private String phEquiR;

    /**
     * 预估权益比例
     */
    @Column(name = "ph_equi_x")
    private String phEquiX;

    /**
     * 预估状态达成时间（权益比例）
     */
    @Column(name = "ph_equi_t")
    private String phEquiT;

    /**
     * 当前并表类型
     */
    @Column(name = "ca_typ")
    private String caTyp;

    /**
     * 预估并表类型
     */
    @Column(name = "ca_typ_x")
    private String caTypX;

    /**
     * 预估状态达成时间（并表类型）
     */
    @Column(name = "ca_typ_t")
    private String caTypT;

    /**
     * 项目预算状态
     */
    @Column(name = "pr_bugets")
    private String prBugets;

    /**
     * 项目获取时的状态
     */
    @Column(name = "ph_prgets")
    private String phPrgets;

    /**
     * 项目获取方式
     */
    @Column(name = "pr_gettyp")
    private String prGettyp;

    /**
     * 收并购类型
     */
    @Column(name = "ph_ma_typ")
    private String phMaTyp;

    /**
     * 是否预算内
     */
    @Column(name = "buget_flg")
    private String bugetFlg;

    /**
     * 是否老盘新作
     */
    @Column(name = "ph_od_flg")
    private String phOdFlg;

    /**
     * 交付日期
     */
    @Column(name = "ph_delive")
    private Date phDelive;

    /**
     * 客研土地分类
     */
    @Column(name = "ph_landcl")
    private String phLandcl;

    /**
     * 产品建筑定档
     */
    @Column(name = "arch_set")
    private String archSet;

    /**
     * 产品精装定档
     */
    @Column(name = "hardc_set")
    private String hardcSet;

    /**
     * 产品景观定档
     */
    @Column(name = "lands_set")
    private String landsSet;

    /**
     * 计税方式
     */
    @Column(name = "tax_typ")
    private String taxTyp;

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
     * 获取分期身份证
     *
     * @return ph_id - 分期身份证
     */
    public String getPhId() {
        return phId;
    }

    /**
     * 设置分期身份证
     *
     * @param phId 分期身份证
     */
    public void setPhId(String phId) {
        this.phId = phId == null ? null : phId.trim();
    }

    /**
     * 获取SAP版本
     *
     * @return sap_ver - SAP版本
     */
    public String getSapVer() {
        return sapVer;
    }

    /**
     * 设置SAP版本
     *
     * @param sapVer SAP版本
     */
    public void setSapVer(String sapVer) {
        this.sapVer = sapVer == null ? null : sapVer.trim();
    }

    /**
     * 获取版本名称
     *
     * @return ver_nam - 版本名称
     */
    public String getVerNam() {
        return verNam;
    }

    /**
     * 设置版本名称
     *
     * @param verNam 版本名称
     */
    public void setVerNam(String verNam) {
        this.verNam = verNam == null ? null : verNam.trim();
    }

    /**
     * 获取历史编码
     *
     * @return his_code - 历史编码
     */
    public String getHisCode() {
        return hisCode;
    }

    /**
     * 设置历史编码
     *
     * @param hisCode 历史编码
     */
    public void setHisCode(String hisCode) {
        this.hisCode = hisCode == null ? null : hisCode.trim();
    }

    /**
     * 获取历史身份证
     *
     * @return his_icard - 历史身份证
     */
    public String getHisIcard() {
        return hisIcard;
    }

    /**
     * 设置历史身份证
     *
     * @param hisIcard 历史身份证
     */
    public void setHisIcard(String hisIcard) {
        this.hisIcard = hisIcard == null ? null : hisIcard.trim();
    }

    /**
     * 获取历史id
     *
     * @return his_guid - 历史id
     */
    public String getHisGuid() {
        return hisGuid;
    }

    /**
     * 设置历史id
     *
     * @param hisGuid 历史id
     */
    public void setHisGuid(String hisGuid) {
        this.hisGuid = hisGuid == null ? null : hisGuid.trim();
    }

    /**
     * 获取历史标识
     *
     * @return his_flg - 历史标识
     */
    public String getHisFlg() {
        return hisFlg;
    }

    /**
     * 设置历史标识
     *
     * @param hisFlg 历史标识
     */
    public void setHisFlg(String hisFlg) {
        this.hisFlg = hisFlg == null ? null : hisFlg.trim();
    }

    /**
     * 获取分期名称
     *
     * @return ph_name - 分期名称
     */
    public String getPhName() {
        return phName;
    }

    /**
     * 设置分期名称
     *
     * @param phName 分期名称
     */
    public void setPhName(String phName) {
        this.phName = phName == null ? null : phName.trim();
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

    /**
     * 获取分期案名
     *
     * @return ph_cname - 分期案名
     */
    public String getPhCname() {
        return phCname;
    }

    /**
     * 设置分期案名
     *
     * @param phCname 分期案名
     */
    public void setPhCname(String phCname) {
        this.phCname = phCname == null ? null : phCname.trim();
    }

    /**
     * 获取项目公司
     *
     * @return pr_compan - 项目公司
     */
    public String getPrCompan() {
        return prCompan;
    }

    /**
     * 设置项目公司
     *
     * @param prCompan 项目公司
     */
    public void setPrCompan(String prCompan) {
        this.prCompan = prCompan == null ? null : prCompan.trim();
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
     * 获取产品定档
     *
     * @return ph_prdlev - 产品定档
     */
    public String getPhPrdlev() {
        return phPrdlev;
    }

    /**
     * 设置产品定档
     *
     * @param phPrdlev 产品定档
     */
    public void setPhPrdlev(String phPrdlev) {
        this.phPrdlev = phPrdlev == null ? null : phPrdlev.trim();
    }

    /**
     * 获取研发管控等级
     *
     * @return ph_devlev - 研发管控等级
     */
    public String getPhDevlev() {
        return phDevlev;
    }

    /**
     * 设置研发管控等级
     *
     * @param phDevlev 研发管控等级
     */
    public void setPhDevlev(String phDevlev) {
        this.phDevlev = phDevlev == null ? null : phDevlev.trim();
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
     * 获取操盘类型
     *
     * @return ph_optyp - 操盘类型
     */
    public String getPhOptyp() {
        return phOptyp;
    }

    /**
     * 设置操盘类型
     *
     * @param phOptyp 操盘类型
     */
    public void setPhOptyp(String phOptyp) {
        this.phOptyp = phOptyp == null ? null : phOptyp.trim();
    }

    /**
     * 获取当前权益比例
     *
     * @return ph_equi_r - 当前权益比例
     */
    public String getPhEquiR() {
        return phEquiR;
    }

    /**
     * 设置当前权益比例
     *
     * @param phEquiR 当前权益比例
     */
    public void setPhEquiR(String phEquiR) {
        this.phEquiR = phEquiR == null ? null : phEquiR.trim();
    }

    /**
     * 获取预估权益比例
     *
     * @return ph_equi_x - 预估权益比例
     */
    public String getPhEquiX() {
        return phEquiX;
    }

    /**
     * 设置预估权益比例
     *
     * @param phEquiX 预估权益比例
     */
    public void setPhEquiX(String phEquiX) {
        this.phEquiX = phEquiX == null ? null : phEquiX.trim();
    }

    /**
     * 获取预估状态达成时间（权益比例）
     *
     * @return ph_equi_t - 预估状态达成时间（权益比例）
     */
    public String getPhEquiT() {
        return phEquiT;
    }

    /**
     * 设置预估状态达成时间（权益比例）
     *
     * @param phEquiT 预估状态达成时间（权益比例）
     */
    public void setPhEquiT(String phEquiT) {
        this.phEquiT = phEquiT == null ? null : phEquiT.trim();
    }

    /**
     * 获取当前并表类型
     *
     * @return ca_typ - 当前并表类型
     */
    public String getCaTyp() {
        return caTyp;
    }

    /**
     * 设置当前并表类型
     *
     * @param caTyp 当前并表类型
     */
    public void setCaTyp(String caTyp) {
        this.caTyp = caTyp == null ? null : caTyp.trim();
    }

    /**
     * 获取预估并表类型
     *
     * @return ca_typ_x - 预估并表类型
     */
    public String getCaTypX() {
        return caTypX;
    }

    /**
     * 设置预估并表类型
     *
     * @param caTypX 预估并表类型
     */
    public void setCaTypX(String caTypX) {
        this.caTypX = caTypX == null ? null : caTypX.trim();
    }

    /**
     * 获取预估状态达成时间（并表类型）
     *
     * @return ca_typ_t - 预估状态达成时间（并表类型）
     */
    public String getCaTypT() {
        return caTypT;
    }

    /**
     * 设置预估状态达成时间（并表类型）
     *
     * @param caTypT 预估状态达成时间（并表类型）
     */
    public void setCaTypT(String caTypT) {
        this.caTypT = caTypT == null ? null : caTypT.trim();
    }

    /**
     * 获取项目预算状态
     *
     * @return pr_bugets - 项目预算状态
     */
    public String getPrBugets() {
        return prBugets;
    }

    /**
     * 设置项目预算状态
     *
     * @param prBugets 项目预算状态
     */
    public void setPrBugets(String prBugets) {
        this.prBugets = prBugets == null ? null : prBugets.trim();
    }

    /**
     * 获取项目获取时的状态
     *
     * @return ph_prgets - 项目获取时的状态
     */
    public String getPhPrgets() {
        return phPrgets;
    }

    /**
     * 设置项目获取时的状态
     *
     * @param phPrgets 项目获取时的状态
     */
    public void setPhPrgets(String phPrgets) {
        this.phPrgets = phPrgets == null ? null : phPrgets.trim();
    }

    /**
     * 获取项目获取方式
     *
     * @return pr_gettyp - 项目获取方式
     */
    public String getPrGettyp() {
        return prGettyp;
    }

    /**
     * 设置项目获取方式
     *
     * @param prGettyp 项目获取方式
     */
    public void setPrGettyp(String prGettyp) {
        this.prGettyp = prGettyp == null ? null : prGettyp.trim();
    }

    /**
     * 获取收并购类型
     *
     * @return ph_ma_typ - 收并购类型
     */
    public String getPhMaTyp() {
        return phMaTyp;
    }

    /**
     * 设置收并购类型
     *
     * @param phMaTyp 收并购类型
     */
    public void setPhMaTyp(String phMaTyp) {
        this.phMaTyp = phMaTyp == null ? null : phMaTyp.trim();
    }

    /**
     * 获取是否预算内
     *
     * @return buget_flg - 是否预算内
     */
    public String getBugetFlg() {
        return bugetFlg;
    }

    /**
     * 设置是否预算内
     *
     * @param bugetFlg 是否预算内
     */
    public void setBugetFlg(String bugetFlg) {
        this.bugetFlg = bugetFlg == null ? null : bugetFlg.trim();
    }

    /**
     * 获取是否老盘新作
     *
     * @return ph_od_flg - 是否老盘新作
     */
    public String getPhOdFlg() {
        return phOdFlg;
    }

    /**
     * 设置是否老盘新作
     *
     * @param phOdFlg 是否老盘新作
     */
    public void setPhOdFlg(String phOdFlg) {
        this.phOdFlg = phOdFlg == null ? null : phOdFlg.trim();
    }

    /**
     * 获取交付日期
     *
     * @return ph_delive - 交付日期
     */
    public Date getPhDelive() {
        return phDelive;
    }

    /**
     * 设置交付日期
     *
     * @param phDelive 交付日期
     */
    public void setPhDelive(Date phDelive) {
        this.phDelive = phDelive;
    }

    /**
     * 获取客研土地分类
     *
     * @return ph_landcl - 客研土地分类
     */
    public String getPhLandcl() {
        return phLandcl;
    }

    /**
     * 设置客研土地分类
     *
     * @param phLandcl 客研土地分类
     */
    public void setPhLandcl(String phLandcl) {
        this.phLandcl = phLandcl == null ? null : phLandcl.trim();
    }

    /**
     * 获取产品建筑定档
     *
     * @return arch_set - 产品建筑定档
     */
    public String getArchSet() {
        return archSet;
    }

    /**
     * 设置产品建筑定档
     *
     * @param archSet 产品建筑定档
     */
    public void setArchSet(String archSet) {
        this.archSet = archSet == null ? null : archSet.trim();
    }

    /**
     * 获取产品精装定档
     *
     * @return hardc_set - 产品精装定档
     */
    public String getHardcSet() {
        return hardcSet;
    }

    /**
     * 设置产品精装定档
     *
     * @param hardcSet 产品精装定档
     */
    public void setHardcSet(String hardcSet) {
        this.hardcSet = hardcSet == null ? null : hardcSet.trim();
    }

    /**
     * 获取产品景观定档
     *
     * @return lands_set - 产品景观定档
     */
    public String getLandsSet() {
        return landsSet;
    }

    /**
     * 设置产品景观定档
     *
     * @param landsSet 产品景观定档
     */
    public void setLandsSet(String landsSet) {
        this.landsSet = landsSet == null ? null : landsSet.trim();
    }

    /**
     * 获取计税方式
     *
     * @return tax_typ - 计税方式
     */
    public String getTaxTyp() {
        return taxTyp;
    }

    /**
     * 设置计税方式
     *
     * @param taxTyp 计税方式
     */
    public void setTaxTyp(String taxTyp) {
        this.taxTyp = taxTyp == null ? null : taxTyp.trim();
    }
}