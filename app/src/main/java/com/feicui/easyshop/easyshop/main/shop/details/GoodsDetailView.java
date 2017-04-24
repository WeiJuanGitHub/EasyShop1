package com.feicui.easyshop.easyshop.main.shop.details;

import com.feicui.easyshop.easyshop.model.GoodsDetail;
import com.feicui.easyshop.easyshop.model.User;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public interface GoodsDetailView extends MvpView{

    void showProgress();

    void hideProgress();

    //设置图片路径
    void setImageData(ArrayList<String> arrayList);

    //设置商品信息
    void setData(GoodsDetail data, User goods_user);

    //*商品不存在了*/
    void showError();

    //提示信息
    void showMessage(String msg);

    //*删除商品*/
    void deleteEnd();
}
