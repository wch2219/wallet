package com.hjzn.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjzn.wallet.MyApplication;
import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_loginpwd)
    LinearLayout llLoginpwd;
    @BindView(R.id.ll_paypwd)
    LinearLayout llPaypwd;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.sett);
        if (MyApplication.userInfo.getData().getTransactionPassword() == null) {
            tv_name.setText(R.string.setPaypwd);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.ll_loginpwd, R.id.ll_paypwd})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_loginpwd:
                startActivity(new Intent(ctx,ChangeLoginPwdActivity.class));
                break;
            case R.id.ll_paypwd:
                startActivity(new Intent(ctx,ChangePayPwdActivity.class));
                break;
        }
    }
}
