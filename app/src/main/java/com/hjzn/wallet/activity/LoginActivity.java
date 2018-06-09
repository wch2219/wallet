package com.hjzn.wallet.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hjzn.wallet.MainActivity;
import com.hjzn.wallet.MyApplication;
import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.LoginBean;
import com.hjzn.wallet.model.PublicMode;
import com.hjzn.wallet.model.SmsBean;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.ParamKey;
import com.hjzn.wallet.utils.SpUtiles;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_regist)
    TextView loginRegist;
    @BindView(R.id.login_reset_pwd)
    TextView loginResetPwd;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.login);
        tvTitle.setTextColor(getResources().getColor(R.color.white));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CONTACTS, Manifest.permission.CAMERA
            }, 1002);
        }
    }

    @Override
    protected void initListener() {

    }
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    @OnClick({R.id.login_btn, R.id.login_regist, R.id.login_reset_pwd})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.login_regist:
//                forRegister();
                intent = new Intent(ctx, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_reset_pwd:
                intent = new Intent(ctx, ResetPwdActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        HttpType = 0;
        String username = loginUsername.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            vibrator.vibrate(100);
            Toast.makeText(ctx, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = loginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            vibrator.vibrate(100);
            Toast.makeText(ctx, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        mPreenter.fetch(map,true, Api.Login,"");
    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        wch(s);
        if (HttpType == 1) {//短信
            SmsBean smsBean = gson.fromJson(s, SmsBean.class);
            startRegister(smsBean.getData().getVerificationCode());
        }else if (HttpType == 2){//注册
            PublicMode publicMode = gson.fromJson(s, PublicMode.class);
            if (publicMode.getMsg().isSuccess()) {
                forRegister();
            }
        }else{

            LoginBean loginBean =  gson.fromJson(s,LoginBean.class);
            if (!loginBean.getMsg().isSuccess()) {
                Toast.makeText(ctx, loginBean.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
                return;
            }
            LoginBean.DataBean data = loginBean.getData();
            MyApplication.setLoginData(data);
            sp.edit().putString(SpUtiles.UserId,data.getUserId()).commit();
            sp.edit().putString(SpUtiles.UserName,data.getNickName()).commit();
            Intent intent = new Intent(ctx,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            exitBy2Click();
        }


        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
        }
    private String phone="186370519";
    private String phone1="";
    private int num = 10;
    private int HttpType = 0;
    private void forRegister() {
        phone1 = phone+num;
        if (num >48) {
            Toast.makeText(ctx, "完毕", Toast.LENGTH_SHORT).show();
            wch("完毕");
            return;
        }
        num++;
        wch(phone1);
        HttpType = 1;
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", phone1);
        mPreenter.fetch(map, true, Api.SendSms, "");

    }
    private  void startRegister(String sms){
        HttpType = 2;
        Map<String, String> params = new HashMap<>();
        params.put(ParamKey.PASSWORD, "w.s0123");
        params.put(ParamKey.MOBILE, phone1);
        params.put(ParamKey.VERIFICATIONCODE, sms);
        params.put(ParamKey.INVITATIONCODE, "VC10001");
        mPreenter.fetch(params,false, Api.Register,"");
    }
}
