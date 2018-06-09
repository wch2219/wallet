package com.hjzn.wallet.activity;

import android.content.Intent;
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
import com.hjzn.wallet.model.SmsBean;
import com.hjzn.wallet.utils.Api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 */
public class PhoneVerActivity extends BaseActivity {

    public static final String VISIT_NUM_KEY = "visitNum";
    public static final String REGIST_PHONE_KEY = "phone";
    private static final String HINT_FORMAT = "请输入%s收到的验证码";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.fast_regist_phone_hint)
    TextView fastRegistPhoneHint;
    @BindView(R.id.fast_regist_ver_num)
    EditText fastRegistVerNum;
    @BindView(R.id.fast_regist_get_ver)
    Button fastRegistGetVer;
    @BindView(R.id.fast_regist_next)
    Button fastRegistNext;
    private String mPhone;
    private String mVisit;
    private int HttpType;//0短信验证  1  注册
    private String verificationCode;
    private CountDownTimer countDownTimer;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_phone_ver);
    }

    @Override
    protected void initData() {
        tvTitle.setText("注册");
        initTabBar(toolBar);
        mPhone = getIntent().getStringExtra(REGIST_PHONE_KEY);
        mVisit = getIntent().getStringExtra(VISIT_NUM_KEY);
        fastRegistPhoneHint.setText(String.format(HINT_FORMAT, mPhone));
        sendSms();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.fast_regist_get_ver, R.id.fast_regist_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fast_regist_get_ver:
                if (!ischek) {

                    sendSms();
                }
                break;
            case R.id.fast_regist_next:
                next();
                break;
        }
    }

    private void next() {
        String code = fastRegistVerNum.getText().toString().trim();
        if (TextUtils.isEmpty(code) && !verificationCode.equals(code)) {
            vibrator.vibrate(100);
            Toast.makeText(ctx, "验证码输入错误", Toast.LENGTH_SHORT).show();
            fastRegistVerNum.setText("");
            return;
        }

        Intent setPwd = new Intent(ctx, SetPwdActivity.class);
        setPwd.putExtra(SetPwdActivity.VER_KEY, code);
        setPwd.putExtra(SetPwdActivity.VISIT_NUM_KEY, mVisit);
        setPwd.putExtra(SetPwdActivity.PHONE_NUM, mPhone);
        startActivity(setPwd);
        finish();
    }

    private void sendSms() {
        HttpType = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mPhone);
        mPreenter.fetch(map, true, Api.SendSms, "");
    }
    private boolean ischek;

    @Override
    public void showData(String s) throws IOException {
        dissProgress();

        if (HttpType == 0) {
            SmsBean smsBean = gson.fromJson(s, SmsBean.class);
            if (smsBean.getMsg().isSuccess()) {
                countDownTimer = new CountDownTimer(30 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        ischek = true;
                        if (fastRegistGetVer != null)
                        fastRegistGetVer.setText(millisUntilFinished / 1000 + "s");
                    }

                    @Override
                    public void onFinish() {
                        ischek = false;
                        if (fastRegistGetVer != null)
                        fastRegistGetVer.setText("验证码");
                    }
                }.start();
                verificationCode = smsBean.getData().getVerificationCode();
            } else {
                Toast.makeText(ctx, "发送失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError() {
        ischek = false;
        dissProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.onFinish();
    }
}
