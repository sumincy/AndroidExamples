package com.yung.android.basic.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yung.android.common.app.CommonApplication;

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

    protected List<AppCompatActivity> activityStack;

    private StringBuilder sb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityStack = ((CommonApplication) getApplication()).getActivityStack();
        sb = ((CommonApplication) getApplication()).getLogCache();

        activityStack.add(this);

        sb.append(String.format("%s：onCreate()", this));
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        sb.append(String.format("%s：onNewIntent()", this));
        sb.append(System.getProperty("line.separator"));
    }


    protected void printStack() {
        Log.i(TAG, "------" + activityStack.toString() + "------");
    }

    protected void printCurrent() {
        Log.i(TAG, "------" + activityStack.toString() + "------");
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        sb.append(String.format("%s：onSaveInstanceState()", this));
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sb.append(String.format("%s：onRestoreInstanceState()", this));
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        sb.append(String.format("%s：onStart()", this));
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sb.append(String.format("%s：onRestart()", this));
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        sb.append(String.format("%s：onConfigurationChanged()", this));
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sb.append(String.format("%s：onResume()", this));
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        sb.append(String.format("%s：onPause()", this));
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onStop() {
        super.onStop();

        sb.append(String.format("%s：onStop()", this));
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onDestroy() {
        activityStack.remove(this);

        sb.append(String.format("%s：onDestroy()", this));
        sb.append(System.getProperty("line.separator"));
        super.onDestroy();
    }

    protected String getLifecycleLog() {
        return sb.toString();
    }
}
