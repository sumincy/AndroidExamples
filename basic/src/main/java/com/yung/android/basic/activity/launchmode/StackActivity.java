package com.yung.android.basic.activity.launchmode;

import android.os.Bundle;
import android.util.Log;

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
public class StackActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    protected List<AppCompatActivity> activityStack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityStack = ((CommonApplication) getApplication()).getActivityStack();

        activityStack.add(this);
    }

    @Override
    protected void onDestroy() {
        activityStack.remove(this);
        super.onDestroy();
    }

    protected void printStack() {
        Log.i(TAG, "------" + activityStack.toString() + "------");
    }

    protected void printCurrent() {
        Log.i(TAG, "------" + activityStack.toString() + "------");
    }
}
