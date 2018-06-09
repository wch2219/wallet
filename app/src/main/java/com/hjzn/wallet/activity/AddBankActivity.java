package com.hjzn.wallet.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.BankCard;
import com.hjzn.wallet.model.PublicMode;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.NumberUtils;
import com.hjzn.wallet.utils.ParamKey;
import com.hjzn.wallet.utils.SpUtiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加银行卡
 */
public class AddBankActivity extends BaseActivity {
    public static final String BANK_DATA_KEY = "bank_data";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.card_et_owner)
    EditText cardEtOwner;
    @BindView(R.id.card_et_phone)
    EditText cardEtPhone;
    @BindView(R.id.card_et_card_num)
    EditText cardEtCardNum;
    @BindView(R.id.add_bank_card_confirm)
    Button addBankCardConfirm;
    private BankCard.DataBean dataBean;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_bank);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.add_bank_hint);
        initTabBar(toolBar);
        dataBean = (BankCard.DataBean) getIntent().getSerializableExtra(BANK_DATA_KEY);
        if (dataBean != null) {
            cardEtOwner.setText(dataBean.getBankName());
            cardEtCardNum.setText(dataBean.getBankNum());
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.add_bank_card_confirm)
    public void onViewClicked() {
        String bankName = cardEtOwner.getText().toString().trim();
        String bankNum = cardEtCardNum.getText().toString().trim();
        if (!TextUtils.isEmpty(bankName) && !TextUtils.isEmpty(bankNum)) {
            String bankNumCheckResult = NumberUtils.luhmCheck(bankNum);
            if (!"true".equalsIgnoreCase(bankNumCheckResult)) {
                Toast.makeText(ctx, bankNumCheckResult, Toast.LENGTH_SHORT).show();
                return;
            }
            String userId = sp.getString(SpUtiles.UserId, "");
            Map<String, String> map = new HashMap<>();
            map.put(ParamKey.USERID, userId);
            map.put(ParamKey.BANK_NUM, bankNum);
            map.put(ParamKey.BANK_NAME, bankName);
            map.put(ParamKey.IS_DEFAULT, "1");
            if (null != dataBean) {
                map.put("id", dataBean.getId() + "");
            }
            mPreenter.fetch(map, false, Api.AddBank, "");
        } else {
            Toast.makeText(ctx, "请完善开户行及银行卡卡号信息", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        PublicMode publicMode = gson.fromJson(s, PublicMode.class);
        if (publicMode.getMsg().isSuccess()) {
            finish();
        } else {
            Toast.makeText(ctx, publicMode.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
        }
    }
}
