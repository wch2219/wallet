package com.hjzn.wallet.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.hjzn.wallet.MainActivity;
import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.PublicMode;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.ParamKey;
import com.hjzn.wallet.utils.RegularUils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置密码
 */
public class SetPwdActivity extends BaseActivity {
    public static final String VER_KEY = "ver_key";
    public static final String VISIT_NUM_KEY = "visitNum";
    public static final String PHONE_NUM = "phone";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.set_pwd_password)
    EditText setPwdPassword;
    @BindView(R.id.set_pwd_showpwd)
    CheckBox setPwdShowpwd;
    @BindView(R.id.set_pwd_complete)
    Button setPwdComplete;
    private String mVerNum;
    private String mVisit;
    private String mPhone;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_set_pwd);
    }

    @Override
    protected void initData() {
        tvTitle.setText("设置密码");
        initTabBar(toolBar);
        mVerNum = getIntent().getStringExtra(VER_KEY);
        mVisit = getIntent().getStringExtra(VISIT_NUM_KEY);
        mPhone = getIntent().getStringExtra(PHONE_NUM);
    }

    @Override
    protected void initListener() {
        setPwdShowpwd.setOnCheckedChangeListener(changeListener);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.set_pwd_complete)
    public void onViewClicked() {
        String pwd = setPwdPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(pwd) && pwd.length() >= 6) {
            if (RegularUils.isNumeric(pwd) && pwd.length() < 10) {
                Toast.makeText(ctx, "密码不能为10位以下纯数字", Toast.LENGTH_SHORT).show();
                return;
            }
            if (RegularUils.isAllEnglishChar(pwd) && pwd.length() < 10) {
                Toast.makeText(ctx, "密码不能为10位以下纯英文", Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String, String> params = new HashMap<>();
            params.put(ParamKey.PASSWORD, pwd);
            params.put(ParamKey.MOBILE, mPhone);
            params.put(ParamKey.VERIFICATIONCODE, mVerNum);
            params.put(ParamKey.INVITATIONCODE, mVisit);
            mPreenter.fetch(params,false, Api.Register,"");
        } else {
            Toast.makeText(ctx, "请输入正确的密码", Toast.LENGTH_SHORT).show();
        }
    }
    CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                setPwdPassword.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                setPwdPassword.setInputType((EditorInfo.TYPE_CLASS_TEXT | EditorInfo
                        .TYPE_TEXT_VARIATION_PASSWORD));
            }
        }
    };

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        PublicMode mode = gson.fromJson(s, PublicMode.class);
        if (mode.getMsg().isSuccess()) {
            Toast.makeText(ctx, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(ctx, mode.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
        }
    }
}
