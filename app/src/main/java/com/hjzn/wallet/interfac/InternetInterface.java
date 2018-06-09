package com.hjzn.wallet.interfac;

import java.util.Map;


public interface InternetInterface {

    /**
     * 加载数据
     */
    void loadData(LoadListener loadListener, Map<String, Object> map, boolean isget, String url, String cachKey);

    /**
     * 加载数据监听
     */
    interface LoadListener {
        /**
         * 加载完成回调
         */
        void complete(String s);

        void onError();
    }
}
