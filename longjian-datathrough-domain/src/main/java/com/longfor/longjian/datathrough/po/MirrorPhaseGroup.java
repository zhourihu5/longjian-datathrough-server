package com.longfor.longjian.datathrough.po;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Table(name = "mirror_phase_group")
public class MirrorPhaseGroup {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    /**
     * 分期身份证
     */
    @Column(name = "ph_id")
    private String phId;

    @Column(name = "gr_id")
    private String grId;

    @Column(name = "his_id")
    private String hisId;

    @Column(name = "his_flg")
    private String hisFlg;

    @Column(name = "gr_name")
    private String grName;

    @Column(name = "gr_cname")
    private String grCname;

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
     * @return gr_id
     */
    public String getGrId() {
        return grId;
    }

    /**
     * @param grId
     */
    public void setGrId(String grId) {
        this.grId = grId == null ? null : grId.trim();
    }

    /**
     * @return his_id
     */
    public String getHisId() {
        return hisId;
    }

    /**
     * @param hisId
     */
    public void setHisId(String hisId) {
        this.hisId = hisId == null ? null : hisId.trim();
    }

    /**
     * @return his_flg
     */
    public String getHisFlg() {
        return hisFlg;
    }

    /**
     * @param hisFlg
     */
    public void setHisFlg(String hisFlg) {
        this.hisFlg = hisFlg == null ? null : hisFlg.trim();
    }

    /**
     * @return gr_name
     */
    public String getGrName() {
        return grName;
    }

    /**
     * @param grName
     */
    public void setGrName(String grName) {
        this.grName = grName == null ? null : grName.trim();
    }

    /**
     * @return gr_cname
     */
    public String getGrCname() {
        return grCname;
    }

    /**
     * @param grCname
     */
    public void setGrCname(String grCname) {
        this.grCname = grCname == null ? null : grCname.trim();
    }

    /**
     * @return de_flg
     */
    public String getDeFlg() {
        return deFlg;
    }

    /**
     * @param deFlg
     */
    public void setDeFlg(String deFlg) {
        this.deFlg = deFlg == null ? null : deFlg.trim();
    }
}