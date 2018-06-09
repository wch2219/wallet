package com.hjzn.wallet.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjzn.wallet.R;
import com.hjzn.wallet.adapter.BankListAdapter;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.BankCard;
import com.hjzn.wallet.model.PublicMode;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.ParamKey;
import com.hjzn.wallet.utils.SpUtiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankListActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.manage_address_img_add)
    ImageView manageAddressImgAdd;
    @BindView(R.id.manage_address_tv_add)
    TextView manageAddressTvAdd;
    @BindView(R.id.rl_addbank)
    RelativeLayout rlAddbank;
    private int HttpType;//1 银行卡列表，2设置为默认，3 删除
    private List<BankCard.DataBean> bankCardData;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_bank_list);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.bankmanage);
        initTabBar(toolBar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getBankList();
    }

    private void getBankList() {
        HttpType = 1;
        String userId = sp.getString(SpUtiles.UserId, "");
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        mPreenter.fetch(map,true, Api.BankList,"Api.BankList"+userId);
    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int type = getIntent().getIntExtra("type", 0);
                if (type == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("bank",bankCardData.get(position));
                    setResult(1,intent);
                    finish();
                }
            }
        });
    }
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.rl_addbank)
    public void onViewClicked() {
        startActivity(new Intent(ctx,AddBankActivity.class));
    }
    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        if (HttpType == 1) {
            BankCard bankCard = gson.fromJson(s, BankCard.class);
            if (bankCard.getMsg().isSuccess()) {
                bankCardData = bankCard.getData();
                BankListAdapter bankListAdapter = new BankListAdapter(ctx, bankCardData);
                lvList.setAdapter(bankListAdapter);
                bankListAdapter.setCheckListener(checkListener);
            }else{
                Toast.makeText(ctx, bankCard.getMsg().getInfo(), Toast.LENGTH_SHORT).show();

            }
        }else if (HttpType == 2) {
            PublicMode publicMode = gson.fromJson(s, PublicMode.class);
            if (publicMode.getMsg().isSuccess()) {
                getBankList();
            }else{
                Toast.makeText(ctx, publicMode.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
            }
        }else {
            PublicMode publicMode = gson.fromJson(s, PublicMode.class);
            if (publicMode.getMsg().isSuccess()) {
                getBankList();
            }else{
                Toast.makeText(ctx, publicMode.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    private BankListAdapter.CheckListener checkListener = new BankListAdapter.CheckListener() {
        @Override
        public void setDefault(String id,String bankname,String bankcode) {
                HttpType = 2;
            String userId = sp.getString(SpUtiles.UserId, "");
            Map<String,String> map = new HashMap<>();
            map.put(ParamKey.USERID, userId);
            map.put(ParamKey.BANK_NUM, bankcode);
            map.put(ParamKey.BANK_NAME, bankname);
            map.put(ParamKey.IS_DEFAULT, "1");
            map.put("id", id);
            mPreenter.fetch(map, false,Api.AddBank,"");
        }

        @Override
        public void setEdit(BankCard.DataBean dataBean) {
                Intent intent = new Intent(ctx,AddBankActivity.class);
                intent.putExtra(AddBankActivity.BANK_DATA_KEY,dataBean);
                startActivity(intent);
        }

        @Override
        public void setDelete(final String id) {
                HttpType = 3;
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.deleteBank).setMessage(R.string.deleteBankHint);
            builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Map<String,Object> map = new HashMap<>();
                    map.put("userId",sp.getString(SpUtiles.UserId,""));
                    map.put("id",id);
                    mPreenter.fetch(map,true,Api.DeleteBank,"");
                }
            });
            builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                }
            });
            builder.show();
        }
    };
}
