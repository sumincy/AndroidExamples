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
import com.yung.android.basic.databinding.ActivityOrderedBroadcastReceiverBinding;
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
@Route(path = PagePath.ACTIVITY_BROADCAST_ORDERED)
public class OrderedBroadCastActivity extends AppCompatActivity {

    private ActivityOrderedBroadcastReceiverBinding binding;

    private static final String ORDERED_ACTION = "com.yung.android.example.orderedbroadcast";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderedBroadcastReceiverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }


    /**
     * 有序广播 sendOrderedBroadcast 发出的广播可以在 receiver中 通过 abortBroadcast 中断 并可以通过 setResultData传递数据
     * 普通广播 sendBroadcast  与有序广播一样 priority 起作用
     *
     */

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

        binding.btnSendOrdered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(ORDERED_ACTION);
                intent.putExtra("sender", NameUtil.getName(OrderedBroadCastActivity.this) + " time：" + TimeUtil.getTime());

                intent.setPackage(getPackageName());
                sendOrderedBroadcast(intent, null);
                Logger.d(NameUtil.getName(OrderedBroadCastActivity.this) + "：sendOrderedBroadcast");

            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(ORDERED_ACTION);
                intent.putExtra("sender", NameUtil.getName(OrderedBroadCastActivity.this) + " time：" + TimeUtil.getTime());

                intent.setPackage(getPackageName());
                sendBroadcast(intent);
                Logger.d(NameUtil.getName(OrderedBroadCastActivity.this) + "：sendOrderedBroadcast");

            }
        });
    }

}
