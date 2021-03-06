package com.feicui.easyshop.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * 商品详情的实体类
 *
 * code": 1,
 "msg": " success",
 "datas"
 */

public class GoodsDetailResult {

    private int code;
    @SerializedName("msg")
    private String message;
    private GoodsDetail datas;
    //发布者的信息
    private User user;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public GoodsDetail getDatas() {
        return datas;
    }

    public User getUser() {
        return user;
    }
}
