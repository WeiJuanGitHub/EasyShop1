package com.feicui.easyshop.easyshop.user.login;

import android.util.Log;

import com.feicui.easyshop.easyshop.model.CachePreferences;
import com.feicui.easyshop.easyshop.model.User;
import com.feicui.easyshop.easyshop.model.UserResult;
import com.feicui.easyshop.easyshop.network.EasyShopClient;
import com.feicui.easyshop.easyshop.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by dian on 2017/4/19.
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    // TODO: 2017/4/19 0019 环信相关

    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call !=null) call.cancel();
    }

    public void login(String username, String password){
        getView().showPrb();
        call = EasyShopClient.getInstance().login(username, password);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                UserResult userResult = new Gson().fromJson(body,UserResult.class);
                if (userResult.getCode() == 1){
                    //保存用户登录信息到本地配置
                    User user = userResult.getUser();
                    Log.e("aaa","name = "+user.getName());
                    CachePreferences.setUser(user);
                    getView().loginSuccess();
                    getView().showMsg("登录成功");
                }else if (userResult.getCode() == 2){
                    getView().hidePrb();
                    getView().showMsg(userResult.getMessage());
                    getView().loginFailed();
                }else{
                    getView().hidePrb();
                    getView().showMsg("未知错误");
                }
            }
        });
    }

}
