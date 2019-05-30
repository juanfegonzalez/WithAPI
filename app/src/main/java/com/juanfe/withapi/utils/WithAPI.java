package com.juanfe.withapi.utils;

import android.app.Application;
import android.content.Context;

public class WithAPI extends Application {

    private static Context scontext;

    public void onCreate() {
        super.onCreate();
        WithAPI.scontext = getApplicationContext();
    }

    public static Context getAppContext() {
        return WithAPI.scontext;
    }

}
