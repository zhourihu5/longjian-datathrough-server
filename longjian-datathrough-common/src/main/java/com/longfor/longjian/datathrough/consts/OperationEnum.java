package com.longfor.longjian.datathrough.consts;

/**
 * Created by Wang on 2018/11/28.
 */
public enum OperationEnum {

    ADD("ADD", "新增"),UPDATE("UPDATE", "修改"),DEL("DEL", "删除");

    private String  type;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    OperationEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
