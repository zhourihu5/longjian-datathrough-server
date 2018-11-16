package com.longfor.longjian.datathrough.app.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;
import java.util.List;

/**
 * 重写返回类
 * @param <T>
 */
public class LFResultBean<T> implements Serializable {
    public static final String SUCCESS = "0";
    public static final String  FAIL = "1";
    public static final String  TOKEN_FAIL = "-1";
    public static final String TOKEN = "2";
    public static final String OTHER = "9";
    private static final long serialVersionUID = -6136676590276244039L;
    private String message = "success";
    private String result = "0";
    private T data;

    public LFResultBean() {
    }

    public LFResultBean(T data) {
        this.data = data;
    }

    public LFResultBean(Throwable e) {
        this.message = e.toString();
        this.result = "1";
    }

    public <E> E converDataEntity(Class<E> clazz) {
        return JSON.parseObject(JSON.toJSONString(this.data, new SerializerFeature[]{SerializerFeature.WriteNonStringValueAsString}), clazz);
    }

    public JSONObject converDataJSONObject() {
        return JSON.parseObject(JSON.toJSONString(this.data, new SerializerFeature[]{SerializerFeature.WriteNonStringValueAsString}));
    }

    public <E> List<E> converDataArray(Class<E> clazz) {
        return JSON.parseArray(JSON.toJSONString(this.data, new SerializerFeature[]{SerializerFeature.WriteNonStringValueAsString}), clazz);
    }

    public JSONArray converDataJSONArray() {
        return JSON.parseArray(JSON.toJSONString(this.data, new SerializerFeature[]{SerializerFeature.WriteNonStringValueAsString}));
    }


    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
