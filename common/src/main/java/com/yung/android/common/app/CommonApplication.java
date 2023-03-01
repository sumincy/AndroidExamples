package com.yung.android.common.app;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.yung.android.common.ui.wiget.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class CommonApplication extends Application {

    protected List<AppCompatActivity> activityStack = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(this);
    }

    public List<AppCompatActivity> getActivityStack() {
        return activityStack;
    }

    @Override
    public void onTerminate() {
        activityStack.clear();
        super.onTerminate();
    }
}
