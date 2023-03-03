package com.yung.android.basic.activity.broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.yung.android.basic.databinding.ActivityStaticBroadcastReceiverBinding;
import com.yung.android.basic.receiver.MyBroadCastReceiver;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    :
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_BROADCAST_STATIC)
public class StaticBroadcastActivity extends AppCompatActivity {

    private ActivityStaticBroadcastReceiverBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaticBroadcastReceiverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
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
                intent.setAction("com.yung.android.example.staticbroadcast");
                intent.putExtra("sender", NameUtil.getName(StaticBroadcastActivity.this));
//                intent.setClassName(getPackageName(), MyBroadCastReceiver.class.getName());
                intent.setPackage(getPackageName());
                sendBroadcast(intent);
                Logger.d(NameUtil.getName(StaticBroadcastActivity.this) + "：sendBroadcast");
            }
        });
    }
}
