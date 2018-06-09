package com.hjzn.wallet.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
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
import com.hjzn.wallet.utils.ParamKey;
import com.hjzn.wallet.utils.SpUtiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 实名认证
 */
public class RealNameActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_idcart)
    EditText etIdcart;
    @BindView(R.id.tv_idcart)
    TextView tvIdcart;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private UserInfo mUserInfo;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_real_name);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.realname);
        initTabBar(toolBar);
        mUserInfo = MyApplication.userInfo;
        if (null != mUserInfo) {
            if (TextUtils.isEmpty(mUserInfo.getData().getIdCard())) {
                etIdcart.setEnabled(true);
                etName.setEnabled(true);
                btnSubmit.setVisibility(View.VISIBLE);
            } else {
                etIdcart.setEnabled(false);
                etIdcart.setGravity(Gravity.RIGHT);
                etName.setEnabled(false);
                etName.setGravity(Gravity.RIGHT);
                btnSubmit.setVisibility(View.GONE);
                etIdcart.setBackgroundResource(0);
                etName.setBackgroundResource(0);
                etName.setText(mUserInfo.getData().getNickName());
                etIdcart.setText(mUserInfo.getData().getIdCard());
            }
        } else {
            Toast.makeText(ctx, "请检查您的网络", Toast.LENGTH_SHORT).show();
        }
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
        String userId = sp.getString(SpUtiles.UserId, "");
        String personId = etIdcart.getText().toString().trim();
        String name = etName.getText().toString().trim();
        if (!TextUtils.isEmpty(personId)) {
            boolean flag = false;
            //正则匹配身份证格式,缺陷是未检验日期的正确性
            Pattern p = Pattern.compile("(^[1-8][0-7]{2}\\d{3}([12]\\d{3})(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}([0-9Xx])$)");

            Matcher m = p.matcher(personId);
            //匹配最后一位检验码是否正确
            int index[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            //检验码对应规则，第三位实际上应该是x，这个地方用100但是实际上检验时不会用到
            int check[] = {1, 0, 100, 9, 8, 7, 6, 5, 4, 3, 2};
            if (m.matches()) {
                int sum = 0;
                for (int i = 0; i < 17; i++) sum += index[i] * (personId.charAt(i) - '0');
                sum %= 11;
                if (sum == 2 && (personId.charAt(17) == 'x' || personId.charAt(17) == 'X'))
                    flag = true;
                else if (check[sum] == (personId.charAt(17) - '0')) flag = true;
                Map<String, Object> params = new HashMap<>();
                params.put(ParamKey.USERID, userId);
                params.put(ParamKey.ID_CARD, personId);
                params.put(ParamKey.NICK_NAME, name);
                mPreenter.fetch(params, false, Api.RealName, "");
            } else {
                Toast.makeText(ctx, "您输入的身份证号有误", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        PublicMode publicMode = gson.fromJson(s, PublicMode.class);
        if (publicMode.getMsg().isSuccess()) {
            Toast.makeText(ctx, "认证成功", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(ctx, publicMode.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
        }
    }
}
