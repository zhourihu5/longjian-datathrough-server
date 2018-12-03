package com.longfor.longjian.datathrough.po;

import javax.persistence.*;

@Table(name = "zrre_bst")
public class ZrreBst {
    @Id
    private Integer id;

    private String mandt;

    @Column(name = "ztree_ver")
    private String ztreeVer;

    private String znode;

    private String nodeid;

    @Column(name = "znode_name")
    private String znodeName;

    private String namej;

    private String namee;

    private String zcity;

    @Column(name = "zcity_name")
    private String zcityName;

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
     * @return ztree_ver
     */
    public String getZtreeVer() {
        return ztreeVer;
    }

    /**
     * @param ztreeVer
     */
    public void setZtreeVer(String ztreeVer) {
        this.ztreeVer = ztreeVer == null ? null : ztreeVer.trim();
    }

    /**
     * @return znode
     */
    public String getZnode() {
        return znode;
    }

    /**
     * @param znode
     */
    public void setZnode(String znode) {
        this.znode = znode == null ? null : znode.trim();
    }

    /**
     * @return nodeid
     */
    public String getNodeid() {
        return nodeid;
    }

    /**
     * @param nodeid
     */
    public void setNodeid(String nodeid) {
        this.nodeid = nodeid == null ? null : nodeid.trim();
    }

    /**
     * @return znode_name
     */
    public String getZnodeName() {
        return znodeName;
    }

    /**
     * @param znodeName
     */
    public void setZnodeName(String znodeName) {
        this.znodeName = znodeName == null ? null : znodeName.trim();
    }

    /**
     * @return namej
     */
    public String getNamej() {
        return namej;
    }

    /**
     * @param namej
     */
    public void setNamej(String namej) {
        this.namej = namej == null ? null : namej.trim();
    }

    /**
     * @return namee
     */
    public String getNamee() {
        return namee;
    }

    /**
     * @param namee
     */
    public void setNamee(String namee) {
        this.namee = namee == null ? null : namee.trim();
    }

    /**
     * @return zcity
     */
    public String getZcity() {
        return zcity;
    }

    /**
     * @param zcity
     */
    public void setZcity(String zcity) {
        this.zcity = zcity == null ? null : zcity.trim();
    }

    /**
     * @return zcity_name
     */
    public String getZcityName() {
        return zcityName;
    }

    /**
     * @param zcityName
     */
    public void setZcityName(String zcityName) {
        this.zcityName = zcityName == null ? null : zcityName.trim();
    }
}