package com.hjzn.wallet.base;

import java.io.IOException;


/**
 * 父类接口
 */
public interface ViewInterface {
    /**
     * 显示加载框
     */
    void showProgress();

    /**
     * 隐藏加载框
     */
    void dissProgress();

    /**
     * 显示错误界面
     */
    void showErrorView();

    /**
     * 显示网络错误提示
     */
    void showInterError();

    /**
     * 显示空数据界面
     */
    void showEntryView();
    /**
     * 显示数据
     * @param s
     */
    void showData(String s) throws IOException;
    void onError();
}
