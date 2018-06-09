package com.hjzn.wallet.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.AccountBank;
import com.hjzn.wallet.utils.Api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.confirm_account)
    EditText confirmAccount;
    @BindView(R.id.reset_phone)
    EditText resetPhone;
    @BindView(R.id.reset_btn_confirm)
    Button resetBtnConfirm;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_forget);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.forget_pwd_title);
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick(R.id.reset_btn_confirm)
    public void onViewClicked() {
        String account = confirmAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            vibrator.vibrate(100);
            Toast.makeText(ctx, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String bankcode = resetPhone.getText().toString().trim();
        if (TextUtils.isEmpty(bankcode)) {
            vibrator.vibrate(100);
            Toast.makeText(ctx, "银行卡不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("userName",account);
        map.put("bankNum",bankcode);
        mPreenter.fetch(map,true, Api.ForgrtPwd,"");
    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        AccountBank accountBank = gson.fromJson(s, AccountBank.class);
        if (accountBank.getMsg().isSuccess()) {
            Intent resetPwd = new Intent(ctx, ResetPwdActivity
                    .class);
            startActivity(resetPwd);
            finish();
        }else{
            Toast.makeText(ctx, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }
}
