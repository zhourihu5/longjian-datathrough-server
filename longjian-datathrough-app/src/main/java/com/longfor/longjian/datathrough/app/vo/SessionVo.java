package com.longfor.longjian.datathrough.app.vo;

import lombok.Data;

/**
 * Created by Wang on 2018/11/19.
 */

@Data
public class SessionVo {

    private String Path;

    private String Domain;

    private int MaxAge;

    private boolean Secure;

    private boolean HttpOnly;
}
