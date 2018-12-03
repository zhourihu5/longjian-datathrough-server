package com.longfor.longjian.datathrough.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "adpt_group")
public class AdptGroup {
    @Id
    @Column(name = "auto_id")
    private Integer autoId;

    /**
     * 组团身份证
     */
    @Column(name = "gr_id")
    private String grId;

    /**
     * 历史编码
     */
    @Column(name = "his_id")
    private String hisId;

    /**
     * 历史标识
     */
    @Column(name = "his_flg")
    private String hisFlg;

    /**
     * 组团名称
     */
    @Column(name = "gr_name")
    private String grName;

    /**
     * 组团小案名
     */
    @Column(name = "gr_cname")
    private String grCname;

    /**
     * 删除标记
     */
    @Column(name = "de_flg")
    private String deFlg;

    /**
     * 是否含宴会厅
     */
    @Column(name="banqu_flg")
    private String banquFlg;

    /**
     * 游泳池情况
     */
    @Column(name="swimcond")
    private String swimcond;

    /**
     * 分期身份证
     */
    @Column(name="ph_id")
    private String phId;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

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
     * 获取组团身份证
     *
     * @return gr_id - 组团身份证
     */
    public String getGrId() {
        return grId;
    }

    /**
     * 设置组团身份证
     *
     * @param grId 组团身份证
     */
    public void setGrId(String grId) {
        this.grId = grId == null ? null : grId.trim();
    }

    /**
     * 获取历史编码
     *
     * @return his_id - 历史编码
     */
    public String getHisId() {
        return hisId;
    }

    /**
     * 设置历史编码
     *
     * @param hisId 历史编码
     */
    public void setHisId(String hisId) {
        this.hisId = hisId == null ? null : hisId.trim();
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
     * 获取组团名称
     *
     * @return gr_name - 组团名称
     */
    public String getGrName() {
        return grName;
    }

    /**
     * 设置组团名称
     *
     * @param grName 组团名称
     */
    public void setGrName(String grName) {
        this.grName = grName == null ? null : grName.trim();
    }

    /**
     * 获取组团小案名
     *
     * @return gr_cname - 组团小案名
     */
    public String getGrCname() {
        return grCname;
    }

    /**
     * 设置组团小案名
     *
     * @param grCname 组团小案名
     */
    public void setGrCname(String grCname) {
        this.grCname = grCname == null ? null : grCname.trim();
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

    public String getPhId() {
        return phId;
    }

    public void setPhId(String phId) {
        this.phId = phId;
    }

    public String getBanquFlg() {
        return banquFlg;
    }

    public void setBanquFlg(String banquFlg) {
        this.banquFlg = banquFlg;
    }

    public String getSwimcond() {
        return swimcond;
    }

    public void setSwimcond(String swimcond) {
        this.swimcond = swimcond;
    }
}