package com.hjzn.wallet.utils;

import android.support.constraint.BuildConfig;


/**
 * Created by xuxingchen on 2017/11/11.
 * 网络相关的静态常量，静态变量等相关参数
 */
public class NetConstant {

    public static final String JS_HANDLER_NAME = "HJHandler";

    /**
     * 官网地址
     */
    public static final String OFFICIAL_URL = "http://www.zxhjcn.com";

    //测试接口地址
    public static final String BASE_URL_TEST = "http://122.114.5.231:8080/";
//    public static final String BASE_URL_TEST = "http://122.114.5.231:8080/";


    //正式接口地址
    //  public static final String BAES_UEL_REALSE = "http://122.114.161.3:8080/";
    public static final String BAES_UEL_REALSE = "http://122.114.5.231:8080/";

    public static final String BASE_WEB_URL_TEST = "http://122.114.161.3:8088";

    public static final String BASE_WEB_URL_RELEASE = "http://122.114.5.231:8188";

    public static final String BASE_WEB_URL = BuildConfig.DEBUG ? BASE_WEB_URL_TEST :
            BASE_WEB_URL_RELEASE;

    //接口地址
    public static final String BASE_URL = BuildConfig.DEBUG ? BASE_URL_TEST : BAES_UEL_REALSE;
//    public static final String BASE_URL = BuildConfig.DEBUG ? BASE_URL_TEST : BASE_URL_TEST;
    /**
     * 图片路径
     */
    public static final String IMGAE_PATH = BASE_URL + "/api/showImg/";


    public static final String GSCPrice = "http://www.btcb9.com/getDateByID.htm?scid=35";

}
