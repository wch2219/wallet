package com.hjzn.wallet.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;


import com.hjzn.wallet.MainActivity;
import com.hjzn.wallet.MyApplication;
import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.UserInfo;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.SpUtiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LauncherActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String userId = sp.getString(SpUtiles.UserId, "");
            if (TextUtils.isEmpty(userId)) {
                Intent mainIntent = new Intent(ctx, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }else{
                Intent mainIntent = new Intent(ctx, MainActivity.class);
                startActivity(mainIntent);
                finish();

            }

        }
    };
    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
    }
}
