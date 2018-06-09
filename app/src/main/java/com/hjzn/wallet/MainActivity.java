package com.hjzn.wallet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hjzn.wallet.activity.BankListActivity;
import com.hjzn.wallet.activity.LoginActivity;
import com.hjzn.wallet.activity.MenMoneyActivity;
import com.hjzn.wallet.activity.PersionActivity;
import com.hjzn.wallet.activity.RealNameActivity;
import com.hjzn.wallet.activity.RecomActivity;
import com.hjzn.wallet.activity.SettingActivity;
import com.hjzn.wallet.activity.WipDepActivity;
import com.hjzn.wallet.base.BaseActivity;
import com.hjzn.wallet.base.Presenter;
import com.hjzn.wallet.model.UserInfo;
import com.hjzn.wallet.utils.Api;
import com.hjzn.wallet.utils.NetConstant;
import com.hjzn.wallet.utils.SetBanner;
import com.hjzn.wallet.utils.SpUtiles;
import com.hjzn.wallet.weight.CircleImageView;
import com.hjzn.wallet.weight.RaiseNumberAnimTextView;
import com.tencent.bugly.beta.Beta;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_end)
    RelativeLayout rlEnd;
    @BindView(R.id.tv_totalnum)
    RaiseNumberAnimTextView tvTotalnum;
    @BindView(R.id.tv_usernum)
    RaiseNumberAnimTextView tvUsernum;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.nav)
    NavigationView nav;
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_yejinum)
    TextView tvYejinum;
    private View headerView;
    private TextView tvNackName;
    private CircleImageView ivHead;
    private TextView tvPhone;
    private UserInfo userInfo;
    private List<Integer> banners= new ArrayList<>();
    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.width= width;
        params.height = (int) (width/2.5);
        banner.setLayoutParams(params);
        register();
        //获取头布局
        headerView = nav.getHeaderView(0);
        RelativeLayout ll_head = headerView.findViewById(R.id.ll_head);
        tvNackName = headerView.findViewById(R.id.tv_nickname);
        ivHead = headerView.findViewById(R.id.iv_head);
        tvPhone = headerView.findViewById(R.id.tv_phone);
        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, PersionActivity.class);
                startActivity(intent);
            }
        });
        tvTitle.setText(R.string.title);
        banners.add(R.mipmap.icon_homebanner1);
        banners.add(R.mipmap.iocn_homebanner2);
        SetBanner.startBanner(ctx,banner,banners);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CONTACTS, Manifest.permission.CAMERA
            }, 1002);
        }else {
            Beta.checkUpgrade();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userId = sp.getString(SpUtiles.UserId, "");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        mPreenter.fetch(map, true, Api.GetUserInfo, "");
    }
    @Override
    protected void initListener() {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.history://收益记录
//
//                        break;
                    case R.id.realname://实名认证
                        startActivity(new Intent(ctx,RealNameActivity.class));
                        break;
//                    case R.id.bankcart://银行卡管理
//                        startActivity(new Intent(ctx, BankListActivity.class));
//                        break;
                    case R.id.recomm://推荐
                        startActivity(new Intent(ctx,RecomActivity.class));
                        break;
                    case R.id.sett://设置
                        startActivity(new Intent(ctx, SettingActivity.class));
                        break;
                    case R.id.exit://退出登录
                        startActivity(new Intent(ctx, LoginActivity.class));
                        sp.edit().putString(SpUtiles.UserId, "").commit();
                        finish();
                        Toast.makeText(MainActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
                dlMain.closeDrawer(nav);
                return true;
            }
        });
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void showData(String s) throws IOException {
        dissProgress();
        userInfo = gson.fromJson(s, UserInfo.class);
        MyApplication.userInfo = userInfo;
        if (userInfo.getMsg().isSuccess()) {
            if (TextUtils.isEmpty(userInfo.getData().getAvatarUrl())) {
                ivHead.setImageResource(R.mipmap.icon_head);
            }else{
                Glide.with(ctx).load(NetConstant.IMGAE_PATH+ userInfo.getData().getAvatarUrl()).into(ivHead);
            }
            if (userInfo.getData().getNickName() == null) {
                tvNackName.setText(userInfo.getData().getUserName());
            }else{
                tvNackName.setText(userInfo.getData().getNickName());
            }
            tvPhone.setText(userInfo.getData().getMobile());
            tvRate.setText(userInfo.getData().getGsc_return_speed()+"");
            tvYejinum.setText(userInfo.getData().getIntegration()+"");
            tvVip.setText(userInfo.getData().getRegion());
            tvPrice.setText(userInfo.getData().getBj()+"");
            tvTotalnum.setDuration(2000);
            tvTotalnum.setNumberWithAnim((float) userInfo.getData().getHold_gsc_num());
            tvTotalnum.setAnimInterpolator(new AccelerateInterpolator());
            tvUsernum.setDuration(2000);
            tvUsernum.setNumberWithAnim((float) userInfo.getData().getGsc_num());
            tvUsernum.setAnimInterpolator(new DecelerateInterpolator());

        }else {
            Toast.makeText(ctx, userInfo.getMsg().getInfo(), Toast.LENGTH_SHORT).show();
        }
    }
    private void newThread(){
        new Thread(){
            @Override
            public void run() {
                while (flag){
                        handler.sendEmptyMessage(0);
                        SystemClock.sleep(20);
                }

            }
        }.start();
    }
    @OnClick({R.id.rl_end, R.id.btn_submit,R.id.btn_tixin})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_end:
                if (dlMain.isDrawerOpen(nav)) {
                    dlMain.closeDrawer(nav);
                } else {
                    dlMain.openDrawer(nav);
                }
                break;
            case R.id.btn_submit:
                intent = new Intent(ctx, MenMoneyActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.btn_tixin:
                intent = new Intent(ctx, MenMoneyActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
                break;
        }
    }
    private double total;
    private double userable;
    private boolean flag;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (userable<= userInfo.getData().getGsc_num()) {
                userable+=0.1;
                tvUsernum.setText(userable+"");
            }else{
                tvUsernum.setText(userInfo.getData().getGsc_num()+"");
            }
            if (total<=userInfo.getData().getHold_gsc_num()) {
                total+=0.1;
                tvTotalnum.setText(total+"");
                flag = false;
            }else{
                tvTotalnum.setText(userInfo.getData().getHold_gsc_num()+"");
            }
        }
    };
}
