package com.longfor.longjian.datathrough.po;

import javax.persistence.*;

@Table(name = "zrre_t_fmt_all")
public class ZrreTFmtAll {
    @Id
    private Integer id;

    private String mandt;

    @Column(name = "db_key")
    private String dbKey;

    @Column(name = "channel_num")
    private String channelNum;

    @Column(name = "fmt_fcls")
    private String fmtFcls;

    @Column(name = "fmt_fcls_desc")
    private String fmtFclsDesc;

    @Column(name = "fmt_scls")
    private String fmtScls;

    @Column(name = "fmt_scls_desc")
    private String fmtSclsDesc;

    @Column(name = "fmt_tcls")
    private String fmtTcls;

    @Column(name = "fmt_tcls_desc")
    private String fmtTclsDesc;

    @Column(name = "fmt_attr")
    private String fmtAttr;

    @Column(name = "fmt_attr_desc")
    private String fmtAttrDesc;

    @Column(name = "attr_value")
    private String attrValue;

    @Column(name = "attr_value_desc")
    private String attrValueDesc;

    /**
     * 业态组合分类编码
     */
    @Column(name = "fmt_comb")
    private String fmtComb;

    /**
     * 业态组合分类描述
     */
    @Column(name = "fmt_comb_desc")
    private String fmtCombDesc;

    @Column(name = "fmt_attr_1")
    private String fmtAttr1;

    @Column(name = "attr_value_1")
    private String attrValue1;

    @Column(name = "attr_value_desc_1")
    private String attrValueDesc1;

    @Column(name = "fmt_attr_2")
    private String fmtAttr2;

    @Column(name = "attr_value_2")
    private String attrValue2;

    @Column(name = "attr_value_desc_2")
    private String attrValueDesc2;

    @Column(name = "fmt_attr_3")
    private String fmtAttr3;

    @Column(name = "attr_value_3")
    private String attrValue3;

    @Column(name = "attr_value_desc_3")
    private String attrValueDesc3;

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
     * @return mandt
     */
    public String getMandt() {
        return mandt;
    }

    /**
     * @param mandt
     */
    public void setMandt(String mandt) {
        this.mandt = mandt == null ? null : mandt.trim();
    }

    /**
     * @return db_key
     */
    public String getDbKey() {
        return dbKey;
    }

    /**
     * @param dbKey
     */
    public void setDbKey(String dbKey) {
        this.dbKey = dbKey == null ? null : dbKey.trim();
    }

    /**
     * @return channel_num
     */
    public String getChannelNum() {
        return channelNum;
    }

    /**
     * @param channelNum
     */
    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum == null ? null : channelNum.trim();
    }

    /**
     * @return fmt_fcls
     */
    public String getFmtFcls() {
        return fmtFcls;
    }

    /**
     * @param fmtFcls
     */
    public void setFmtFcls(String fmtFcls) {
        this.fmtFcls = fmtFcls == null ? null : fmtFcls.trim();
    }

    /**
     * @return fmt_fcls_desc
     */
    public String getFmtFclsDesc() {
        return fmtFclsDesc;
    }

    /**
     * @param fmtFclsDesc
     */
    public void setFmtFclsDesc(String fmtFclsDesc) {
        this.fmtFclsDesc = fmtFclsDesc == null ? null : fmtFclsDesc.trim();
    }

    /**
     * @return fmt_scls
     */
    public String getFmtScls() {
        return fmtScls;
    }

    /**
     * @param fmtScls
     */
    public void setFmtScls(String fmtScls) {
        this.fmtScls = fmtScls == null ? null : fmtScls.trim();
    }

    /**
     * @return fmt_scls_desc
     */
    public String getFmtSclsDesc() {
        return fmtSclsDesc;
    }

    /**
     * @param fmtSclsDesc
     */
    public void setFmtSclsDesc(String fmtSclsDesc) {
        this.fmtSclsDesc = fmtSclsDesc == null ? null : fmtSclsDesc.trim();
    }

    /**
     * @return fmt_tcls
     */
    public String getFmtTcls() {
        return fmtTcls;
    }

    /**
     * @param fmtTcls
     */
    public void setFmtTcls(String fmtTcls) {
        this.fmtTcls = fmtTcls == null ? null : fmtTcls.trim();
    }

    /**
     * @return fmt_tcls_desc
     */
    public String getFmtTclsDesc() {
        return fmtTclsDesc;
    }

    /**
     * @param fmtTclsDesc
     */
    public void setFmtTclsDesc(String fmtTclsDesc) {
        this.fmtTclsDesc = fmtTclsDesc == null ? null : fmtTclsDesc.trim();
    }

    /**
     * @return fmt_attr
     */
    public String getFmtAttr() {
        return fmtAttr;
    }

    /**
     * @param fmtAttr
     */
    public void setFmtAttr(String fmtAttr) {
        this.fmtAttr = fmtAttr == null ? null : fmtAttr.trim();
    }

    /**
     * @return fmt_attr_desc
     */
    public String getFmtAttrDesc() {
        return fmtAttrDesc;
    }

    /**
     * @param fmtAttrDesc
     */
    public void setFmtAttrDesc(String fmtAttrDesc) {
        this.fmtAttrDesc = fmtAttrDesc == null ? null : fmtAttrDesc.trim();
    }

    /**
     * @return attr_value
     */
    public String getAttrValue() {
        return attrValue;
    }

    /**
     * @param attrValue
     */
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue == null ? null : attrValue.trim();
    }

    /**
     * @return attr_value_desc
     */
    public String getAttrValueDesc() {
        return attrValueDesc;
    }

    /**
     * @param attrValueDesc
     */
    public void setAttrValueDesc(String attrValueDesc) {
        this.attrValueDesc = attrValueDesc == null ? null : attrValueDesc.trim();
    }

    /**
     * 获取业态组合分类编码
     *
     * @return fmt_comb - 业态组合分类编码
     */
    public String getFmtComb() {
        return fmtComb;
    }

    /**
     * 设置业态组合分类编码
     *
     * @param fmtComb 业态组合分类编码
     */
    public void setFmtComb(String fmtComb) {
        this.fmtComb = fmtComb == null ? null : fmtComb.trim();
    }

    /**
     * 获取业态组合分类描述
     *
     * @return fmt_comb_desc - 业态组合分类描述
     */
    public String getFmtCombDesc() {
        return fmtCombDesc;
    }

    /**
     * 设置业态组合分类描述
     *
     * @param fmtCombDesc 业态组合分类描述
     */
    public void setFmtCombDesc(String fmtCombDesc) {
        this.fmtCombDesc = fmtCombDesc == null ? null : fmtCombDesc.trim();
    }

    /**
     * @return fmt_attr_1
     */
    public String getFmtAttr1() {
        return fmtAttr1;
    }

    /**
     * @param fmtAttr1
     */
    public void setFmtAttr1(String fmtAttr1) {
        this.fmtAttr1 = fmtAttr1 == null ? null : fmtAttr1.trim();
    }

    /**
     * @return attr_value_1
     */
    public String getAttrValue1() {
        return attrValue1;
    }

    /**
     * @param attrValue1
     */
    public void setAttrValue1(String attrValue1) {
        this.attrValue1 = attrValue1 == null ? null : attrValue1.trim();
    }

    /**
     * @return attr_value_desc_1
     */
    public String getAttrValueDesc1() {
        return attrValueDesc1;
    }

    /**
     * @param attrValueDesc1
     */
    public void setAttrValueDesc1(String attrValueDesc1) {
        this.attrValueDesc1 = attrValueDesc1 == null ? null : attrValueDesc1.trim();
    }

    /**
     * @return fmt_attr_2
     */
    public String getFmtAttr2() {
        return fmtAttr2;
    }

    /**
     * @param fmtAttr2
     */
    public void setFmtAttr2(String fmtAttr2) {
        this.fmtAttr2 = fmtAttr2 == null ? null : fmtAttr2.trim();
    }

    /**
     * @return attr_value_2
     */
    public String getAttrValue2() {
        return attrValue2;
    }

    /**
     * @param attrValue2
     */
    public void setAttrValue2(String attrValue2) {
        this.attrValue2 = attrValue2 == null ? null : attrValue2.trim();
    }

    /**
     * @return attr_value_desc_2
     */
    public String getAttrValueDesc2() {
        return attrValueDesc2;
    }

    /**
     * @param attrValueDesc2
     */
    public void setAttrValueDesc2(String attrValueDesc2) {
        this.attrValueDesc2 = attrValueDesc2 == null ? null : attrValueDesc2.trim();
    }

    /**
     * @return fmt_attr_3
     */
    public String getFmtAttr3() {
        return fmtAttr3;
    }

    /**
     * @param fmtAttr3
     */
    public void setFmtAttr3(String fmtAttr3) {
        this.fmtAttr3 = fmtAttr3 == null ? null : fmtAttr3.trim();
    }

    /**
     * @return attr_value_3
     */
    public String getAttrValue3() {
        return attrValue3;
    }

    /**
     * @param attrValue3
     */
    public void setAttrValue3(String attrValue3) {
        this.attrValue3 = attrValue3 == null ? null : attrValue3.trim();
    }

    /**
     * @return attr_value_desc_3
     */
    public String getAttrValueDesc3() {
        return attrValueDesc3;
    }

    /**
     * @param attrValueDesc3
     */
    public void setAttrValueDesc3(String attrValueDesc3) {
        this.attrValueDesc3 = attrValueDesc3 == null ? null : attrValueDesc3.trim();
    }
}