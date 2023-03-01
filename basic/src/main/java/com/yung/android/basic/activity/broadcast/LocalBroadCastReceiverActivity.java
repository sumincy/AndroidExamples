package com.yung.android.basic.activity.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yung.android.basic.databinding.ActivityLocalBroadcastReceiverBinding;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    :
 *
 *   只是在应用内部来实现通信的时候，可以考虑使用LocalBroadcastManager来实现，它有两个好处
 *  1.1：性能较一般的broadcast要好，因为它的实现不需要经过binder调用，它采用的是handler通信来实现的
 *  1.2：安全性较好，不必担心数据泄露或是被截取
 *
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_BROADCAST_LOCAL)
public class LocalBroadCastReceiverActivity extends AppCompatActivity {

    private ActivityLocalBroadcastReceiverBinding binding;

    private LocalBroadcastManager localBroadcastManager;

    private MyReceiver myReceiver;

    private static final String LOCAL_ACTION = "com.yung.android.example.localbroadcast";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocalBroadcastReceiverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myReceiver = new MyReceiver();

        IntentFilter intentFilter = new IntentFilter(LOCAL_ACTION);
        localBroadcastManager.registerReceiver(myReceiver, intentFilter);
        Logger.d(NameUtil.getName(LocalBroadCastReceiverActivity.this) + "：registerReceiver() 注册");
    }

    private void initViews() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(LOCAL_ACTION);
                localBroadcastManager.sendBroadcast(intent);
                Logger.d(NameUtil.getName(LocalBroadCastReceiverActivity.this) + "：sendBroadcast() 发送广播");

            }
        });
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.e(NameUtil.getName(this) + "：onReceive() 收到广播");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(myReceiver);
        Logger.d(NameUtil.getName(LocalBroadCastReceiverActivity.this) + "：unregisterReceiver() 注销");
    }
}
