package com.hjzn.wallet.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hjzn.wallet.MyApplication;
import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.utils.AndroidShare;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 推荐
 */
public class RecomActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_recomcode)
    TextView tvRecomcode;
    @BindView(R.id.ll_shareqq)
    LinearLayout llShareqq;
    @BindView(R.id.ll_sharqwx)
    LinearLayout llSharqwx;
    @BindView(R.id.ll_pyq)
    LinearLayout llPyq;
    @BindView(R.id.ll_wb)
    LinearLayout llWb;
    private AndroidShare mShare;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_recom);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.recom);
        initTabBar(toolBar);
        mShare = new AndroidShare(this);
    }

    @Override
    protected void initListener() {
        tvRecomcode.setText(MyApplication.userInfo.getData().getRegion());
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.ll_shareqq, R.id.ll_sharqwx, R.id.ll_pyq, R.id.ll_wb})
    public void onViewClicked(View view) {
        if (MyApplication.userInfo.getData().getRegion() == null) {
            Toast.makeText(ctx, "您当前还未获得推荐资格", Toast.LENGTH_SHORT).show();
            return;
        }
        final String content = "RD国际交易所，诚邀您的加入，https://www.pgyer.com/zws3，点击链接下载应用，快快加入吧！我的邀请码是：" + MyApplication.userInfo.getData().getRegion();
        switch (view.getId()) {
            case R.id.ll_shareqq:
                mShare.shareQQFriend("分享给好友", content, AndroidShare.TEXT, (Bitmap) null);
                break;
            case R.id.ll_sharqwx:
                mShare.shareWeChatFriend("分享给好友", content, AndroidShare.TEXT, (Bitmap) null);
                break;
            case R.id.ll_pyq:
                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_logo);
                Glide.with(this).asBitmap().load(resourceIdToUri(this,R.mipmap.icon_logo)).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        mShare.shareWeChatFriendCircle("分享到朋友圈", content, resource);
                    }
                });
                break;
            case R.id.ll_wb:
                Bitmap bitmapWeiBo = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_logo);


                mShare.shareWeiBo("分享给好友", MyApplication.userInfo.getData().getRegion(), AndroidShare.DRAWABLE, bitmapWeiBo);
                break;
        }
    }
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
