package com.feicui.easyshop.easyshop.main.me.personInfo;

import com.feicui.easyshop.easyshop.model.CachePreferences;
import com.feicui.easyshop.easyshop.model.User;
import com.feicui.easyshop.easyshop.model.UserResult;
import com.feicui.easyshop.easyshop.network.EasyShopClient;
import com.feicui.easyshop.easyshop.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created by dian on 2017/4/20.
 */

public class PersonPersenter extends MvpNullObjectBasePresenter<PersonView> {

        private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }

    //上传头像
    public void updataAvatar(File file){
        getView().showPrb();
        call = EasyShopClient.getInstance().uploadAvatar(file);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                getView().hidePrb();
                UserResult result = new Gson().fromJson(body,UserResult.class);

                if (result == null){
                    getView().showMsg("未知错误");
                }else if (result.getCode() != 1){
                    getView().showMsg(result.getMessage());
                    return;
                }

                User user = result.getUser();
                CachePreferences.setUser(user);

                //上传成功，触发UI操作（更新头像）
                getView().updataAvatar(result.getUser().getHead_Image());

                // TODO: 2017/4/20 0020 环信更新头像
            }
        });


    }

}
