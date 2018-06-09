package com.hjzn.wallet.base;

import android.util.Log;


import com.hjzn.wallet.interfac.InternetInterface;
import com.hjzn.wallet.model.InterModelImpl;

import java.io.IOException;
import java.util.Map;


public class Presenter<V>extends ViewPresenter {
    ViewInterface viewInterface;
    InterModelImpl mode = new InterModelImpl();

    public Presenter(ViewInterface viewInterface) {
        super();
        this.viewInterface = viewInterface;
    }
    //绑定view和mode
    public void fetch(Map<String,Object> map, Boolean isget, String url, String cachKey){
        viewInterface.showProgress();

        if (mode != null) mode.loadData(new InternetInterface.LoadListener() {
            @Override
            public void complete(String s) {

                try {
                    viewInterface.showData(s);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("erro",e.getMessage());
                }
            }
            @Override
            public void onError() {
                viewInterface.onError();
            }
        }, map, isget, url, cachKey);

    }
}
