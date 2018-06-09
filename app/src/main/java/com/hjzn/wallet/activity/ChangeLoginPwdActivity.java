package com.hjzn.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hjzn.wallet.MyApplication;
import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.PublicMode;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.ParamKey;
import com.hjzn.wallet.utils.RegularUils;
import com.hjzn.wallet.utils.SpUtiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改登录密码
 */
public class ChangeLoginPwdActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_newpwd)
    EditText etNewpwd;
    @BindView(R.id.et_cofirpwd)
    EditText etCofirpwd;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_login_pwd);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.changeloginpwd);
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String pwd = etNewpwd.getText().toString().trim();
        String confirmPwd = etCofirpwd.getText().toString().trim();
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(confirmPwd)) {
            if (RegularUils.isNumeric(pwd)) {
                Toast.makeText(ctx, "密码不能为纯数字", Toast.LENGTH_SHORT).show();
                return;
            }
            if (RegularUils.isAllEnglishChar(pwd)) {
                Toast.makeText(ctx, "密码不能为纯英文", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pwd.equalsIgnoreCase(confirmPwd)) {
                String userId = sp.getString(SpUtiles.UserId, "");
                Map<String, Object> params = new HashMap<>();
                params.put(ParamKey.USERID, userId);
                params.put(ParamKey.PASSWORD, pwd);
                mPreenter.fetch(params,false, Api.ChangePwd,"");
            } else {
                Toast.makeText(ctx, "两次输入的密码需一致才能继续提交", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ctx, "请填写新的密码并确认", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        PublicMode publicMode = gson.fromJson(s, PublicMode.class);
        if (publicMode.getMsg().isSuccess()) {
            Intent intent = new Intent(ctx,LoginActivity.class);
            startActivity(intent);
            sp.edit().putString(SpUtiles.UserId,"").commit();
            close();
            finish();
        }else{
            Toast.makeText(ctx, publicMode.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
        }
    }
}
