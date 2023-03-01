package com.yung.android.examples.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lzf.easyfloat.EasyFloat;
import com.yung.android.common.app.CommonApplication;
import com.yung.android.examples.BuildConfig;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class MyApplication extends CommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化 arouter
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EasyFloat.dismiss();
    }
}
