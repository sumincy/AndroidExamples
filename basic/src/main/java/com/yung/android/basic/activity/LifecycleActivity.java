package com.yung.android.basic.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.yung.android.basic.activity.broadcast.StaticBroadcastActivity;
import com.yung.android.common.app.CommonApplication;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

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
public class LifecycleActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    private final String NAME = NameUtil.getName(this);

    protected List<AppCompatActivity> activityStack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityStack = ((CommonApplication) getApplication()).getActivityStack();
        activityStack.add(this);

        Logger.i("- onCreate() -" + NAME);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.i("- onNewIntent() -" + NAME);
    }


    protected void printStack() {
        Logger.e("---当前stack中包含：" + activityStack.size() + "个Activity---");
        //TODO
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (int i = 0; i < activityStack.size(); i++) {
            AppCompatActivity activity = activityStack.get(i);

            String name = activity.getClass().getSimpleName() + "@" + Integer.toHexString(activity.hashCode());
            sb.append(name);
            if (i < activityStack.size() - 1) {
                sb.append("--->");
            }
        }
        sb.append("]");
        Logger.d(sb.toString());

    }

    protected void printCurrent() {
        Log.i(TAG, "------" + this + "------");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Logger.i("- onConfigurationChanged() -" + NAME);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.i("- onSaveInstanceState() -" + NAME);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.i("- onRestoreInstanceState() -" + NAME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i("- onStart() -" + NAME);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.i("- onRestart() -" + NAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("- onResume() -" + NAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i("- onPause() -" + NAME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i("- onStop() -" + NAME);
    }

    @Override
    protected void onDestroy() {
        activityStack.remove(this);
        super.onDestroy();
        Logger.i("- onDestroy() -" + NAME);
    }

}
