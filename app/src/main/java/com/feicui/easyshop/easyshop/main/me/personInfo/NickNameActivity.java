package com.feicui.easyshop.easyshop.main.me.personInfo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.feicui.easyshop.easyshop.R;
import com.feicui.easyshop.easyshop.commons.ActivityUtils;
import com.feicui.easyshop.easyshop.commons.RegexUtils;
import com.feicui.easyshop.easyshop.model.CachePreferences;
import com.feicui.easyshop.easyshop.model.User;
import com.feicui.easyshop.easyshop.model.UserResult;
import com.feicui.easyshop.easyshop.network.EasyShopClient;
import com.feicui.easyshop.easyshop.network.UICallBack;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by dian on 2017/4/23.
 */

public class NickNameActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_nickname)
    EditText etNickName;

    private ActivityUtils activityUtils;
    private String str_nickname;
    private Unbinder unbinder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        //实现返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        activityUtils = new ActivityUtils(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_save)
    public void onClick(){
        //拿到用户输入的昵称
        str_nickname = etNickName.getText().toString();
        //判断昵称是否符合规则
        if (RegexUtils.verifyNickname(str_nickname) != RegexUtils.VERIFY_SUCCESS){
            String msg = "昵称为中文，字母或数字，长度为1~12，一个中文算两个长度";
            activityUtils.showToast(msg);
            return;
        }
        init();//修改昵称
    }

    //修改昵称
    private void init() {
        //从本地配置中拿到用户类
        User user = CachePreferences.getUser();
        //把要修改的昵称设置进来
        user.setNick_name(str_nickname);
        //执行修改昵称的网络请求
        Call call = EasyShopClient.getInstance().uploadUser(user);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                activityUtils.showToast(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                UserResult result = new Gson().fromJson(body, UserResult.class);
                //如果修改失败，则跳出
                if (result.getCode() != 1) {
                    activityUtils.showToast(result.getMessage());
                    return;
                }
                //如果修改成功
                CachePreferences.setUser(result.getUser());
                activityUtils.showToast("修改成功");
                //返回
                onBackPressed();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();//解绑
    }
}
