package com.longfor.longjian.datathrough.po;

import com.longfor.longjian.datathrough.domain.innerService.PhaseService;
import tk.mybatis.mapper.annotation.KeySql;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mirror_phase_c_three")
public class MirrorPhaseCThree implements PhaseService {
    @Id
    @KeySql(useGeneratedKeys = true)
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
     * 历史ID
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
     * 主数据分期名称
     */
    @Column(name="tree_phm")
    private String treePhm;

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
     * 项目类型
     */
    @Column(name = "pr_type")
    private String prType;

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
     * 历史项目code
     */
    @Column(name="his_pr_id")
    private String hisPrId;

    /**
     * 操盘类型
     */
    @Column(name = "ph_optyp")
    private String phOptyp;

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
     * 操盘类型（开发阶段）
     */
    @Column(name = "ph_otyp_d")
    private String phOtypD;

    /**
     * 操盘类型（运营阶段）
     */
    @Column(name = "ph_otyp_o")
    private String phOtypO;

    /**
     * 当前权益比例（开发阶段）
     */
    @Column(name = "ph_eq_r_d")
    private String phEqRD;

    /**
     * 预估权益比例（开发阶段）
     */
    @Column(name = "ph_eq_r_x")
    private String phEqRX;

    /**
     * 预估状态达成时间（权益比例开发阶段）
     */
    @Column(name = "ph_eq_r_t")
    private String phEqRT;

    /**
     * 当前权益比例（运营阶段）
     */
    @Column(name = "ph_eq_r_o")
    private String phEqRO;

    /**
     * 预估权益比例（运营阶段）
     */
    @Column(name = "ph_eq_o_x")
    private String phEqOX;

    /**
     * 预估状态达成时间（运营阶段）
     */
    @Column(name = "ph_eq_o_t")
    private String phEqOT;

    /**
     * 产品线
     */
    @Column(name = "ph_prdlin")
    private String phPrdlin;

    /**
     * 客研土地分类
     */
    @Column(name = "ph_landcl")
    private String phLandcl;

    /**
     * 计税方式
     */
    @Column(name = "tax_typ")
    private String taxTyp;

    /**
     * 是否预算内
     */
    @Column(name = "buget_flg")
    private String bugetFlg;

    /**
     * 资产类型
     */
    @Column(name = "ph_asstyp")
    private String phAsstyp;

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
     * 项目获取时的状态
     */
    @Column(name = "ph_prgets")
    private String phPrgets;

    /**
     * 开业日期
     */
    @Column(name = "ph_open_d")
    private Date phOpenD;

    /**
     * 项目预算状态
     */
    @Column(name = "pr_bugets")
    private String prBugets;

    /**
     * 改造成本分级
     */
    @Column(name = "ph_rnlev")
    private String phRnlev;

    /**
     * 房型
     */
    @Column(name = "ph_flplan")
    private String phFlplan;

    /**
     * 承租合同租赁年限（年）
     */
    @Column(name = "ph_leaper")
    private Integer phLeaper;

    /**
     * 承租合同租赁起始日期 （含免租期）
     */
    @Column(name = "ph_ren_sd")
    private Date phRenSd;

    /**
     * 承租合同租赁截止日期
     */
    @Column(name = "ph_ren_ed")
    private Date phRenEd;

    /**
     * 门店地址
     */
    @Column(name = "ph_shpadr")
    private String phShpadr;

    /**
     * 商机名称
     */
    @Column(name = "ph_opname")
    private String phOpname;

    /**
     * 商机编码
     */
    @Column(name = "ph_opid")
    private String phOpid;

    /**
     * 建造类别
     */
    private String constype;

    /**
     * 是否售转持
     */
    @Column(name = "ph_sh_flg")
    private String phShFlg;

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
     * 获取历史ID
     *
     * @return his_guid - 历史ID
     */
    public String getHisGuid() {
        return hisGuid;
    }

    /**
     * 设置历史ID
     *
     * @param hisGuid 历史ID
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
     * 获取项目类型
     *
     * @return pr_type - 项目类型
     */
    public String getPrType() {
        return prType;
    }

    /**
     * 设置项目类型
     *
     * @param prType 项目类型
     */
    public void setPrType(String prType) {
        this.prType = prType == null ? null : prType.trim();
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
     * 获取操盘类型（开发阶段）
     *
     * @return ph_otyp_d - 操盘类型（开发阶段）
     */
    public String getPhOtypD() {
        return phOtypD;
    }

    /**
     * 设置操盘类型（开发阶段）
     *
     * @param phOtypD 操盘类型（开发阶段）
     */
    public void setPhOtypD(String phOtypD) {
        this.phOtypD = phOtypD == null ? null : phOtypD.trim();
    }

    /**
     * 获取操盘类型（运营阶段）
     *
     * @return ph_otyp_o - 操盘类型（运营阶段）
     */
    public String getPhOtypO() {
        return phOtypO;
    }

    /**
     * 设置操盘类型（运营阶段）
     *
     * @param phOtypO 操盘类型（运营阶段）
     */
    public void setPhOtypO(String phOtypO) {
        this.phOtypO = phOtypO == null ? null : phOtypO.trim();
    }

    /**
     * 获取当前权益比例（开发阶段）
     *
     * @return ph_eq_r_d - 当前权益比例（开发阶段）
     */
    public String getPhEqRD() {
        return phEqRD;
    }

    /**
     * 设置当前权益比例（开发阶段）
     *
     * @param phEqRD 当前权益比例（开发阶段）
     */
    public void setPhEqRD(String phEqRD) {
        this.phEqRD = phEqRD == null ? null : phEqRD.trim();
    }

    /**
     * 获取预估权益比例（开发阶段）
     *
     * @return ph_eq_r_x - 预估权益比例（开发阶段）
     */
    public String getPhEqRX() {
        return phEqRX;
    }

    /**
     * 设置预估权益比例（开发阶段）
     *
     * @param phEqRX 预估权益比例（开发阶段）
     */
    public void setPhEqRX(String phEqRX) {
        this.phEqRX = phEqRX == null ? null : phEqRX.trim();
    }

    /**
     * 获取预估状态达成时间（权益比例开发阶段）
     *
     * @return ph_eq_r_t - 预估状态达成时间（权益比例开发阶段）
     */
    public String getPhEqRT() {
        return phEqRT;
    }

    /**
     * 设置预估状态达成时间（权益比例开发阶段）
     *
     * @param phEqRT 预估状态达成时间（权益比例开发阶段）
     */
    public void setPhEqRT(String phEqRT) {
        this.phEqRT = phEqRT == null ? null : phEqRT.trim();
    }

    /**
     * 获取当前权益比例（运营阶段）
     *
     * @return ph_eq_r_o - 当前权益比例（运营阶段）
     */
    public String getPhEqRO() {
        return phEqRO;
    }

    /**
     * 设置当前权益比例（运营阶段）
     *
     * @param phEqRO 当前权益比例（运营阶段）
     */
    public void setPhEqRO(String phEqRO) {
        this.phEqRO = phEqRO == null ? null : phEqRO.trim();
    }

    /**
     * 获取预估权益比例（运营阶段）
     *
     * @return ph_eq_o_x - 预估权益比例（运营阶段）
     */
    public String getPhEqOX() {
        return phEqOX;
    }

    /**
     * 设置预估权益比例（运营阶段）
     *
     * @param phEqOX 预估权益比例（运营阶段）
     */
    public void setPhEqOX(String phEqOX) {
        this.phEqOX = phEqOX == null ? null : phEqOX.trim();
    }

    /**
     * 获取预估状态达成时间（运营阶段）
     *
     * @return ph_eq_o_t - 预估状态达成时间（运营阶段）
     */
    public String getPhEqOT() {
        return phEqOT;
    }

    /**
     * 设置预估状态达成时间（运营阶段）
     *
     * @param phEqOT 预估状态达成时间（运营阶段）
     */
    public void setPhEqOT(String phEqOT) {
        this.phEqOT = phEqOT == null ? null : phEqOT.trim();
    }

    /**
     * 获取产品线
     *
     * @return ph_prdlin - 产品线
     */
    public String getPhPrdlin() {
        return phPrdlin;
    }

    /**
     * 设置产品线
     *
     * @param phPrdlin 产品线
     */
    public void setPhPrdlin(String phPrdlin) {
        this.phPrdlin = phPrdlin == null ? null : phPrdlin.trim();
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
     * 获取资产类型
     *
     * @return ph_asstyp - 资产类型
     */
    public String getPhAsstyp() {
        return phAsstyp;
    }

    /**
     * 设置资产类型
     *
     * @param phAsstyp 资产类型
     */
    public void setPhAsstyp(String phAsstyp) {
        this.phAsstyp = phAsstyp == null ? null : phAsstyp.trim();
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
     * 获取开业日期
     *
     * @return ph_open_d - 开业日期
     */
    public Date getPhOpenD() {
        return phOpenD;
    }

    /**
     * 设置开业日期
     *
     * @param phOpenD 开业日期
     */
    public void setPhOpenD(Date phOpenD) {
        this.phOpenD = phOpenD;
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
     * 获取改造成本分级
     *
     * @return ph_rnlev - 改造成本分级
     */
    public String getPhRnlev() {
        return phRnlev;
    }

    /**
     * 设置改造成本分级
     *
     * @param phRnlev 改造成本分级
     */
    public void setPhRnlev(String phRnlev) {
        this.phRnlev = phRnlev == null ? null : phRnlev.trim();
    }

    /**
     * 获取房型
     *
     * @return ph_flplan - 房型
     */
    public String getPhFlplan() {
        return phFlplan;
    }

    /**
     * 设置房型
     *
     * @param phFlplan 房型
     */
    public void setPhFlplan(String phFlplan) {
        this.phFlplan = phFlplan == null ? null : phFlplan.trim();
    }

    /**
     * 获取承租合同租赁年限（年）
     *
     * @return ph_leaper - 承租合同租赁年限（年）
     */
    public Integer getPhLeaper() {
        return phLeaper;
    }

    /**
     * 设置承租合同租赁年限（年）
     *
     * @param phLeaper 承租合同租赁年限（年）
     */
    public void setPhLeaper(Integer phLeaper) {
        this.phLeaper = phLeaper;
    }

    /**
     * 获取承租合同租赁起始日期 （含免租期）
     *
     * @return ph_ren_sd - 承租合同租赁起始日期 （含免租期）
     */
    public Date getPhRenSd() {
        return phRenSd;
    }

    /**
     * 设置承租合同租赁起始日期 （含免租期）
     *
     * @param phRenSd 承租合同租赁起始日期 （含免租期）
     */
    public void setPhRenSd(Date phRenSd) {
        this.phRenSd = phRenSd;
    }

    /**
     * 获取承租合同租赁截止日期
     *
     * @return ph_ren_ed - 承租合同租赁截止日期
     */
    public Date getPhRenEd() {
        return phRenEd;
    }

    /**
     * 设置承租合同租赁截止日期
     *
     * @param phRenEd 承租合同租赁截止日期
     */
    public void setPhRenEd(Date phRenEd) {
        this.phRenEd = phRenEd;
    }

    /**
     * 获取门店地址
     *
     * @return ph_shpadr - 门店地址
     */
    public String getPhShpadr() {
        return phShpadr;
    }

    /**
     * 设置门店地址
     *
     * @param phShpadr 门店地址
     */
    public void setPhShpadr(String phShpadr) {
        this.phShpadr = phShpadr == null ? null : phShpadr.trim();
    }

    /**
     * 获取商机名称
     *
     * @return ph_opname - 商机名称
     */
    public String getPhOpname() {
        return phOpname;
    }

    /**
     * 设置商机名称
     *
     * @param phOpname 商机名称
     */
    public void setPhOpname(String phOpname) {
        this.phOpname = phOpname == null ? null : phOpname.trim();
    }

    /**
     * 获取商机编码
     *
     * @return ph_opid - 商机编码
     */
    public String getPhOpid() {
        return phOpid;
    }

    /**
     * 设置商机编码
     *
     * @param phOpid 商机编码
     */
    public void setPhOpid(String phOpid) {
        this.phOpid = phOpid == null ? null : phOpid.trim();
    }

    /**
     * 获取建造类别
     *
     * @return constype - 建造类别
     */
    public String getConstype() {
        return constype;
    }

    /**
     * 设置建造类别
     *
     * @param constype 建造类别
     */
    public void setConstype(String constype) {
        this.constype = constype == null ? null : constype.trim();
    }

    /**
     * 获取是否售转持
     *
     * @return ph_sh_flg - 是否售转持
     */
    public String getPhShFlg() {
        return phShFlg;
    }

    /**
     * 设置是否售转持
     *
     * @param phShFlg 是否售转持
     */
    public void setPhShFlg(String phShFlg) {
        this.phShFlg = phShFlg == null ? null : phShFlg.trim();
    }

    public String getTreePhm() {
        return treePhm;
    }

    public void setTreePhm(String treePhm) {
        this.treePhm = treePhm;
    }

    public String getHisPrId() {
        return hisPrId;
    }

    public void setHisPrId(String hisPrId) {
        this.hisPrId = hisPrId;
    }
}