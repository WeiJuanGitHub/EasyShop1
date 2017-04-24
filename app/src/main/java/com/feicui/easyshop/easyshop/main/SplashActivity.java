package com.feicui.easyshop.easyshop.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.feicui.easyshop.easyshop.R;
import com.feicui.easyshop.easyshop.commons.ActivityUtils;
import com.feicui.easyshop.easyshop.model.CachePreferences;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dian on 2017/4/14.
 */

public class SplashActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;//Alt+回车   导包

//快捷键重写
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activityUtils = new ActivityUtils(this);

        //判断用户是否登录，自动登录
        if(CachePreferences.getUser().getName() != null){
            activityUtils.startActivity(MainActivity.class);
            finish();
            return;
        }


        // TODO: 2017/4/14 0014 环信登录相关（账号冲突踢出）

        //1.5s跳转到主页
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //跳转到主页
                activityUtils.startActivity(MainActivity.class);
                finish();
            }
        },1500);
    }
}
