package com.yung.android.basic.activity.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.yung.android.basic.databinding.ActivityDynamicBroadcastReceiverBinding;
import com.yung.android.basic.receiver.MyBroadCastReceiver;
import com.yung.android.basic.receiver.HighLevelBroadCastReceiver;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;
import com.yung.android.common.util.TimeUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    :
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_BROADCAST_DYNAMIC)
public class DynamicBroadcastActivity extends AppCompatActivity {

    private ActivityDynamicBroadcastReceiverBinding binding;

    private MyBroadCastReceiver myBroadCastReceiver;

    private static final String DYNAMIC_ACTION = "com.yung.android.example.dynamicbroadast";
    private static final String ORDERED_ACTION = "com.yung.android.example.orderedbroadcast";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDynamicBroadcastReceiverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myBroadCastReceiver = new MyBroadCastReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DYNAMIC_ACTION);
        registerReceiver(myBroadCastReceiver, intentFilter);

        Logger.d(NameUtil.getName(this) + "：registerReceiver");

    }

    private void initViews() {

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                OnTitleBarListener.super.onTitleClick(titleBar);
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                OnTitleBarListener.super.onRightClick(titleBar);
                Logger.getInstance().loggerSwitch();
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(DYNAMIC_ACTION);
                intent.putExtra("sender", NameUtil.getName(DynamicBroadcastActivity.this));
                sendBroadcast(intent);
                Logger.d(NameUtil.getName(DynamicBroadcastActivity.this) + "：sendBroadcast");

//                Intent intent = new Intent();
//                intent.setAction(STAITC_ACTION);
//                intent.putExtra("sender", NameUtil.getName(DynamicBroadcastActivity.this));
//                intent.setClassName(getPackageName(), MyBroadCastReceiver.class.getName());
//                sendBroadcast(intent);
//                Logger.d(NameUtil.getName(DynamicBroadcastActivity.this) + "：sendBroadcast");


            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadCastReceiver);

        Logger.d(NameUtil.getName(this) + "：unregisterReceiver");
    }
}
