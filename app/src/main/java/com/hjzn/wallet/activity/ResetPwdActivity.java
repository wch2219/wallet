package com.hjzn.wallet.activity;

import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.PublicMode;
import com.hjzn.wallet.model.SmsBean;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.ParamKey;
import com.hjzn.wallet.utils.RegularUils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 重置密码
 */
public class ResetPwdActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.reset_phone)
    EditText resetPhone;
    @BindView(R.id.reset_ver_num)
    EditText resetVerNum;
    @BindView(R.id.reset_get_ver_num)
    Button resetGetVerNum;
    @BindView(R.id.reset_set_new_pwd)
    EditText resetSetNewPwd;
    @BindView(R.id.reset_confirm_new_pwd)
    EditText resetConfirmNewPwd;
    @BindView(R.id.reset_btn_confirm)
    Button resetBtnConfirm;
    private int HttpType;//0 短信 1 重置密码
    private CountDownTimer countDownTimer;
    private String verificationCode;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_reset_pwd);
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

    @OnClick({R.id.reset_get_ver_num, R.id.reset_btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reset_get_ver_num:
                String phone = resetPhone.getText().toString().trim();
                if (RegularUils.isMobileExact(phone)) {
                    sendVerNum(phone);
                } else {
                    Toast.makeText(ctx, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reset_btn_confirm:
                submit();
                break;
        }
    }

    /**
     * 提交修改结果
     */
    private void submit() {
        String tel = resetPhone.getText().toString().trim();
        String ver = resetVerNum.getText().toString().trim();
        String pwd = resetSetNewPwd.getText().toString().trim();
        String confirmPwd = resetConfirmNewPwd.getText().toString().trim();

        if (!TextUtils.isEmpty(tel) && !TextUtils.isEmpty(ver) && !TextUtils.isEmpty(pwd)
                && !TextUtils.isEmpty(confirmPwd)) {
            if (pwd.length() < 6) {
                Toast.makeText(ctx, "密码太短", Toast.LENGTH_SHORT).show();
                return;
            }
            if (RegularUils.isNumeric(pwd)) {
                Toast.makeText(ctx, "密码不能为纯数字", Toast.LENGTH_SHORT).show();
                return;
            }
            if (RegularUils.isAllEnglishChar(pwd)) {
                Toast.makeText(ctx, "密码不能为纯英文", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pwd.equals(confirmPwd)) {
                HttpType = 1;
                Map<String, Object> params = new HashMap<>();
                params.put(ParamKey.USER_NAME, tel);
                params.put(ParamKey.VERIFICATIONCODE, ver);
                params.put(ParamKey.PASSWORD, pwd);
                params.put(ParamKey.MOBILE, tel);
                mPreenter.fetch(params, false, Api.ResetPwd, "");
            } else {
                Toast.makeText(ctx, "两次密码不一致", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ctx, "请填写手机号,验证码,密码,并确认密码", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInSending;

    private void sendVerNum(String phone) {
        if (isInSending) {
            Toast.makeText(ctx, "验证码发送中", Toast.LENGTH_SHORT).show();
        } else {
            HttpType = 0;
            Map<String, Object> map = new HashMap<>();
            map.put("mobile", phone);
            mPreenter.fetch(map, true, Api.SendSms, "");
        }
    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();

        if (HttpType == 0) {
            SmsBean smsBean = gson.fromJson(s, SmsBean.class);

            if (smsBean.getMsg().isSuccess()) {
                countDownTimer = new CountDownTimer(30 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        isInSending = true;
                        if (resetGetVerNum != null) {

                            resetGetVerNum.setText(millisUntilFinished / 1000 + "s");
                        }
                    }

                    @Override
                    public void onFinish() {
                        isInSending = false;
                        if (resetGetVerNum != null) {

                            resetGetVerNum.setText("验证码");
                        }
                    }
                }.start();
                verificationCode = smsBean.getData().getVerificationCode();
            } else {
                Toast.makeText(ctx, "发送失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            PublicMode mode = gson.fromJson(s, PublicMode.class);
            if (mode.getMsg().isSuccess()) {
                Toast.makeText(ctx, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(ctx, "修改失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
