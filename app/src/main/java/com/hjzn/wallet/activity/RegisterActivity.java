package com.hjzn.wallet.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.PublicMode;
import com.hjzn.wallet.utils.Api;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.regist_yaoqingma)
    EditText registYaoqingma;
    @BindView(R.id.regist_phone)
    EditText registPhone;
    @BindView(R.id.regist_next)
    Button registNext;
    private String inviteCode;
    private String phone;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.regist);
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick(R.id.regist_next)
    public void onViewClicked() {
        inviteCode = registYaoqingma.getText().toString().trim();
//        inviteCode = "b550ae419353478f9847a716eae80105";
        if (TextUtils.isEmpty(inviteCode)) {
            vibrator.vibrate(100);
            Toast.makeText(ctx, "邀请码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        phone = registPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            vibrator.vibrate(100);
            Toast.makeText(ctx, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("invitationCode", inviteCode);
        mPreenter.fetch(map,true, Api.InviteCodeCheck,"");
    }
    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        wch(s);
        Gson gson = new Gson();
        PublicMode mode = gson.fromJson(s,PublicMode.class);
        boolean success = mode.getMsg().isSuccess();
        if (success) {
            Intent fastReg = new Intent(ctx, PhoneVerActivity.class);
            fastReg.putExtra(PhoneVerActivity.REGIST_PHONE_KEY, phone);
            fastReg.putExtra(PhoneVerActivity.VISIT_NUM_KEY, inviteCode);
            startActivity(fastReg);
            finish();
        }else{
            Toast.makeText(ctx, mode.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
        }
    }
}
