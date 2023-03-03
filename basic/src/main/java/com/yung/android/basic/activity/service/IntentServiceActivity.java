package com.yung.android.basic.activity.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.yung.android.basic.databinding.ActivityIntentServiceBinding;
import com.yung.android.basic.service.MyIntentService;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/02
 *    desc    :
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_SERVICE_INTENT)
public class IntentServiceActivity extends AppCompatActivity {

    private ActivityIntentServiceBinding binding;

    private final String MY_INTENT_SERVICE_ACTION = "com.yung.android.intent";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntentServiceBinding.inflate(getLayoutInflater());
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
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntentServiceActivity.this, MyIntentService.class);
                Bundle bundle = new Bundle();
                bundle.putString("msg", "class start");
                intent.putExtras(bundle);
                startService(intent);
                Logger.d(NameUtil.getName(IntentServiceActivity.this) + "：startService() " + "---" + "class start");
            }
        });

        binding.btnActionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MY_INTENT_SERVICE_ACTION);

                //使用隐式意图，需要加上包名信息
                intent.setPackage(getPackageName());
                Bundle bundle = new Bundle();
                bundle.putString("msg", "action start");
                intent.putExtras(bundle);
                startService(intent);

                Logger.d(NameUtil.getName(IntentServiceActivity.this) + "：startService() " + "---" + "action start");
            }
        });

        binding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntentServiceActivity.this, MyIntentService.class);
                stopService(intent);

                Logger.d(NameUtil.getName(IntentServiceActivity.this) + "：stopService() " + "---" + "class stop");
            }
        });

        binding.btnActionStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MY_INTENT_SERVICE_ACTION);
                //使用隐式意图，需要加上包名信息
                intent.setPackage(getPackageName());
                stopService(intent);

                Logger.d(NameUtil.getName(IntentServiceActivity.this) + "：stopService() " + "---" + "action stop");
            }
        });
    }
}
