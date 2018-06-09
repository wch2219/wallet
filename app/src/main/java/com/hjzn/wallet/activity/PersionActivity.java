package com.hjzn.wallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hjzn.wallet.MyApplication;
import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.UserInfo;
import com.hjzn.wallet.utils.UploadPicUtiles;
import com.hjzn.wallet.weight.CircleImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人信息
 */
public class PersionActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.person_header_jiantou)
    ImageView personHeaderJiantou;
    @BindView(R.id.person_info_header)
    CircleImageView personInfoHeader;
    @BindView(R.id.person_info_header_container)
    RelativeLayout personInfoHeaderContainer;
    @BindView(R.id.person_user_name)
    TextView personUserName;
    @BindView(R.id.person_info_username_container)
    RelativeLayout personInfoUsernameContainer;
    @BindView(R.id.person_nick_jiantou)
    ImageView personNickJiantou;
    @BindView(R.id.person_nick_name)
    TextView personNickName;
    @BindView(R.id.person_nick_name_container)
    RelativeLayout personNickNameContainer;
    @BindView(R.id.person_card_id_jiantou)
    ImageView personCardIdJiantou;
    @BindView(R.id.person_card_id_name)
    TextView personCardIdName;
    @BindView(R.id.person_card_id_container)
    RelativeLayout personCardIdContainer;
    @BindView(R.id.person_sax_jiantou)
    ImageView personSaxJiantou;
    @BindView(R.id.person_sax)
    TextView personSax;
    @BindView(R.id.person_sax_container)
    RelativeLayout personSaxContainer;
    @BindView(R.id.person_tv_birthday)
    TextView personTvBirthday;
    @BindView(R.id.person_birthday_jiantou)
    ImageView personBirthdayJiantou;
    @BindView(R.id.person_birthday_container)
    RelativeLayout personBirthdayContainer;
    @BindView(R.id.person_submit)
    Button personSubmit;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_persion);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.persion_info);
        initTabBar(toolBar);
        UserInfo userInfo = MyApplication.userInfo;
        personUserName.setText(userInfo.getData().getMobile());
        personNickName.setText(userInfo.getData().getNickName());
        personCardIdName.setText(userInfo.getData().getIdCard());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.person_info_header_container, R.id.person_sax_container, R.id.person_birthday_container, R.id.person_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.person_info_header_container://更换头像
                UploadPicUtiles.showDia(ctx);
                wch(ctx.getPackageName());
                break;
            case R.id.person_sax_container://性别
                break;
            case R.id.person_birthday_container://设置生日
                break;
            case R.id.person_submit://确定
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode){
                case 1:
                    UploadPicUtiles.startZoomPic((Activity) ctx,data,150,150,1,1);
                    break;
                case 2:
                    UploadPicUtiles.startZoomPic((Activity) ctx,data,150,150,1,1);
                    break;
                case 5:
                    File filePath = UploadPicUtiles.getFilePath(data, ctx);
                    wch(filePath+"");
                    Glide.with(ctx).load(filePath).apply(new RequestOptions().centerCrop()).into(personInfoHeader);
//                    UpHead(filePath);
                    break;
            }
        }
    }

}
