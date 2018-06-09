package com.hjzn.wallet.model;

import android.text.TextUtils;

import com.hjzn.wallet.interfac.InternetInterface;
import com.hjzn.wallet.utils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;


import java.util.Iterator;
import java.util.Map;


public class InterModelImpl implements InternetInterface {

    @Override
    public void loadData(final LoadListener loadListener, Map<String,Object> map, boolean isget, String url, String cachKey) {
        if (isget) {
            GetRequest<String> getRequest = OkGo.<String>get(url);
            if (TextUtils.isEmpty(cachKey)) {
                getRequest.cacheMode(CacheMode.NO_CACHE);
            }else{
                getRequest.cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                        .cacheKey(cachKey);
            }
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                String key = next.getKey();
                Object value = next.getValue();
                getRequest.params(key, (String) value);
            }
            getRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    LogUtils.LogI(response.body()+"ssssssss");
                    //加载完成回调
                    loadListener.complete(response.body());

                }

                @Override
                public void onError(Response<String> response) {
                    loadListener.onError();
                }

                @Override
                public void onCacheSuccess(Response<String> response) {
                    onSuccess(response);
                }
            });
        }else{
            PostRequest<String> post = OkGo.<String>post(url);
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                String key = next.getKey();
                Object value = next.getValue();
                post.params(key, (String) value);
            }

            post.execute(new StringCallback() {

                @Override
                public void onSuccess(Response<String> response) {
                    LogUtils.LogI(response.body()+"ssssssss");
                    //加载完成回调
                    loadListener.complete(response.body());
                }

                @Override
                public void onError(Response<String> response) {
                    loadListener.onError();
                }


                @Override
                public void onCacheSuccess(Response<String> response) {
                    onSuccess(response);
                }


            });
        }

    }

}
