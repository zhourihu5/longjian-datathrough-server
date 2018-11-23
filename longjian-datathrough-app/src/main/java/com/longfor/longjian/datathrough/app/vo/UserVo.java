package com.longfor.longjian.datathrough.app.vo;

import lombok.Data;

/**
 * Created by Wang on 2018/11/23.
 */
@Data
public class UserVo {
    private int id;
    private String user_name;
    private String real_name;
    private String email;
    private String mobile;
    private long update_at;
    private long delete_at;
}
