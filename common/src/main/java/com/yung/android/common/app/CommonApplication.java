package com.yung.android.common.app;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

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

    private StringBuilder sb = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<AppCompatActivity> getActivityStack() {
        return activityStack;
    }

    public StringBuilder getLogCache() {
        return sb;
    }

    @Override
    public void onTerminate() {
        activityStack.clear();
        super.onTerminate();
    }
}
