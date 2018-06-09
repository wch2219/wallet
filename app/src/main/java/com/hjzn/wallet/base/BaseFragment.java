package com.hjzn.wallet.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hjzn.wallet.R;
import com.hjzn.wallet.utils.LogUtils;
import com.hjzn.wallet.utils.MyProgressLoading;
import com.hjzn.wallet.utils.SpUtiles;
import com.yinglan.keyboard.HideUtil;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<V,T extends Presenter<V>> extends Fragment implements ViewInterface {

    public Context ctx;
    public T mPreenter;
    public MyProgressLoading loading;
    public SharedPreferences sp;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ctx = getActivity();
        View view = inflater.inflate(getLayoutID(),null);
        mPreenter = createPresenter();
        loading = new MyProgressLoading(ctx, R.style.DialogStyle);
        sp = ctx.getSharedPreferences(SpUtiles.SP_Mode,Context.MODE_PRIVATE);
        HideUtil.init(getActivity());
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        return view;
    }
    /**
     * 获得布局id
     * @return
     */
    public abstract int getLayoutID();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();
    protected abstract T createPresenter();
    @Override
    public void showProgress() {
        if (loading != null&&!loading.isShowing()) {
            loading.show();
        }
    }

    @Override
    public void dissProgress() {
        if (loading != null&&loading.isShowing()) {
            loading.dismiss();
        }
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showInterError() {

    }

    @Override
    public void showEntryView() {

    }

    @Override
    public void showData(String s) throws IOException {

    }

    @Override
    public void onError() {

    }
    public void wch(Object o){
        LogUtils.LogI(o);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
