package com.hjzn.wallet.activity;

import android.app.Activity;
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
import com.hjzn.wallet.model.UserInfo;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.LogUtils;
import com.hjzn.wallet.utils.ParamKey;
import com.hjzn.wallet.utils.PayWindowUtils;
import com.lzy.okgo.adapter.Call;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提笔
 */
public class MenMoneyActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private int type;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_men_money);
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 1);
        tvTitle.setText(R.string.menmoney_title);
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
        PayWindowUtils.show((Activity) ctx);
        PayWindowUtils.setResultCallBack(new PayWindowUtils.ResultCallBack() {
            @Override
            public void result(String result) {
                if (MyApplication.userInfo.getData().getUserId() == null) {
                    Toast.makeText(ctx, "请检查用户信息", Toast.LENGTH_SHORT).show();
                    return;
                }
               goPay(result);
            }
        });
    }

    private void goPay(String result) {
        String mAddress = etAddress.getText().toString().trim();
        String numString = etNum.getText().toString().trim();
        if (!TextUtils.isEmpty(numString) && !TextUtils.isEmpty(mAddress)) {
//            final int num = Integer.valueOf(numString);
            String userId = MyApplication.userInfo.getData().getUserId();
            Map<String, Object> params = new HashMap<>();
            params.put(ParamKey.USERID, userId);
            params.put(ParamKey.AMOUNT, numString);
            params.put(ParamKey.TRANSACTION_PASSWORD, result);
            params.put(ParamKey.INTEGRATION_NUM, numString);
            params.put(ParamKey.MONEY_ADDRESS, mAddress);
//            /**
//             * http://localhost:8080/dataPlatform-mobile-api/gscFlow/sava?userId=3f5fc692a4b145bfae5043b2215ed6fb&amount=1&monyAddren=wetwertwe
//             */
//            params.put(ParamKey.USERID, "3f5fc692a4b145bfae5043b2215ed6fb");
//            params.put(ParamKey.AMOUNT, "1");
//            params.put(ParamKey.TRANSACTION_PASSWORD, result);
//            params.put(ParamKey.INTEGRATION_NUM, "1");
//            params.put(ParamKey.MONEY_ADDRESS, "wetwertwe");
            if (type == 1) {
                mPreenter.fetch(params,false, Api.MenMone,"");
            }else{
                params.put(ParamKey.BANK_NUM, "000000000000000000");
                mPreenter.fetch(params,false,Api.WithDraw,"");
            }
        } else {
            Toast.makeText(ctx, "请输入提币信息,然后提交", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        PublicMode publicMode = gson.fromJson(s, PublicMode.class);
        if (publicMode.getMsg().isSuccess()) {
            Toast.makeText(ctx, "提币成功", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(ctx, publicMode.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
        }
    }
}
