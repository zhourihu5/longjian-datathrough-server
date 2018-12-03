package com.longfor.longjian.datathrough.po;

import lombok.Data;

/**
 * 推送给文档的分期结构
 * Created by Wang on 2018/11/30.
 */
@Data
public class StageConResult {

    /**
     * 公司guid
     */
    private String gsId;

    /**
     * 公司名称
     */
    private String gsName;

    /**
     * 公司code
     */
    private String gsCode;

    /**
     * 项目id
     */
    private String xmId;

    /**
     * 项目名称
     */
    private String xmName;

    /**
     * 项目code
     */
    private String xmCode;

    /**
     * 分期guid
     */
    private String fqId;

    /**
     * 分期名称
     */
    private String fqName;

    /**
     * 分期code
     */
    private String fqCode;

    /**
     * 分期cardid
     */
    private String fqCardId;

    /**
     * 航道
     */
    private String ptype;


}
