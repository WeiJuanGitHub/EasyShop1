package com.feicui.easyshop.easyshop.main.me.personInfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by dian on 2017/4/20.
 */

public interface PersonView extends MvpView {
    void showPrb();

    void hidePrb();

    void showMsg(String msg);
    //用来更新头像
    void updataAvatar(String url);
}
