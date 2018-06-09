package com.hjzn.wallet.base;

import java.lang.ref.WeakReference;

public class ViewPresenter<T>{
    public WeakReference<T> mViewRef;


    /**
     * bind p and v
     * @param view
     */
    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }
    public void detachView(){
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected T getView(){

        return mViewRef.get();
    }
}
