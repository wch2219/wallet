package com.hjzn.wallet;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hjzn.wallet.model.LoginBean;
import com.hjzn.wallet.model.UserInfo;
import com.hjzn.wallet.utils.MyProgressLoading;
import com.lzy.okgo.OkGo;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;





public class MyApplication extends Application {

    public static LoginBean.DataBean loginData;
    public static UserInfo userInfo;

    public static void setLoginData(LoginBean.DataBean loginData) {
        MyApplication.loginData = loginData;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(this, "2b8dacaf2c", false);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
    }
}
