package com.grocery.common;

/**
 * Created by Administrator on 11/06/2017.
 */

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public static int getLanguage(){
        if (LocaleHelper.getLanguage(MyApplication.getInstance().getBaseContext(), LocaleHelper.LOCALE_ENG).equals(LocaleHelper.LOCALE_ENG)) {
            return 2;
        } else {
            return 1;
        }
    }
}