package com.feicui.easyshop.easyshop.user.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by dian on 2017/4/19.
 */

public interface LoginView extends MvpView{
    void showPrb();

    void hidePrb();

    void loginFailed();

    void loginSuccess();

    void showMsg(String msg);
}
