package com.longfor.longjian.datathrough.app.vo;

import lombok.Data;

/**
 * Created by Wang on 2018/11/19.
 */
@Data
public class TokenVo {

    private int uid;

    private int rootTeamId;

    private String token;

    private String username;

    private boolean _permanent;

    private SessionVo sessionVo;

}
