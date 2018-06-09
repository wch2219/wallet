package com.hjzn.wallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjzn.wallet.MyApplication;
import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.BankCard;
import com.hjzn.wallet.model.PublicMode;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.ParamKey;
import com.hjzn.wallet.utils.PayWindowUtils;
import com.hjzn.wallet.utils.SpUtiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现
 */
public class WipDepActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_bankname)
    TextView tvBankname;
    @BindView(R.id.ll_seletebankcard)
    LinearLayout llSeletebankcard;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_totalnum)
    TextView tvTotalnum;
    private int HttpType;//1 银行卡列表/2提现
    private String seletebankinfo;//选择的银行卡信息
    private boolean isrequest = true;//是否请求银行卡列表
    private List<BankCard.DataBean> bankCardData;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wip_dep);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.shouyiytixian);
        tvTotalnum.setText(MyApplication.userInfo.getData().getIntegration()+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isrequest) {
            getBankList();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.ll_seletebankcard, R.id.tv_all, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_seletebankcard:
                startActivityForResult(new Intent(ctx,BankListActivity.class).putExtra("type",1),1);
                break;
            case R.id.tv_all:
                if (MyApplication.userInfo == null) {

                    return;
                }
                double integration = MyApplication.userInfo.getData().getIntegration();
                if (integration >0) {
                    etNum.setText(integration-integration%100+"");
                }
                break;
            case R.id.btn_submit:
                if (seletebankinfo == null) {
                    Toast.makeText(ctx, "请选择银行卡", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String monyNum = etNum.getText().toString().trim();
                if (TextUtils.isEmpty(monyNum)) {
                    Toast.makeText(ctx, "请输入体现数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.valueOf(monyNum)%100 != 0) {
                    Toast.makeText(ctx, "体现金额必须为100的倍数", Toast.LENGTH_SHORT).show();
                    return;
                }
                PayWindowUtils.show((Activity) ctx);
                PayWindowUtils.setResultCallBack(new PayWindowUtils.ResultCallBack() {
                    @Override
                    public void result(String result) {
                        startTixian(monyNum,result);
                    }
                });
                break;
        }
    }

    private void startTixian(String monyNum,String pwd) {
        HttpType = 2;
        Map<String, String> params = new HashMap<>();
        params.put(ParamKey.BANK_NUM, seletebankinfo);
        params.put(ParamKey.INTEGRATION_NUM, monyNum);
        params.put(ParamKey.TRANSACTION_PASSWORD, pwd);
        params.put(ParamKey.USERID, sp.getString(SpUtiles.UserId,""));
        mPreenter.fetch(params,false,Api.WithDraw,"");
    }

    private void getBankList() {
        HttpType = 1;
        String userId = sp.getString(SpUtiles.UserId, "");
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        mPreenter.fetch(map,true, Api.BankList,"Api.BankList"+userId);
    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        if (HttpType == 1) {
            BankCard bankCard = gson.fromJson(s, BankCard.class);
            if (bankCard.getMsg().isSuccess()) {
                bankCardData = bankCard.getData();
                for (int i = 0; i < bankCardData.size(); i++) {
                    BankCard.DataBean dataBean = bankCardData.get(i);
                    int isDefault = dataBean.getIsDefault();
                    if (isDefault == 1) {
                        tvBankname.setText(dataBean.getBankName()+"("+dataBean.getBankNum().substring(dataBean.getBankNum().length()-4)+")");
                        seletebankinfo = dataBean.toString();
                    }
                }
            }else{
                Toast.makeText(ctx, bankCard.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
            }
        }else{
            PublicMode publicMode = gson.fromJson(s, PublicMode.class);
            if (publicMode.getMsg().isSuccess()) {
                Toast.makeText(ctx, "提现成功", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(ctx, publicMode.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        wch(2);
        if (requestCode == 1&&data!=null) {
            isrequest = false;
            BankCard.DataBean dataBean = (BankCard.DataBean) data.getSerializableExtra("bank");
            tvBankname.setText(dataBean.toString());
            seletebankinfo = dataBean.toString();
        }else{
            isrequest = true;
        }
    }
}
