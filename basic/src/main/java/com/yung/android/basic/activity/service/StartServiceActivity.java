package com.yung.android.basic.activity.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yung.android.basic.databinding.ActivityStartServiceBinding;
import com.yung.android.basic.service.LifeCycleService;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    : StartService
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_SERVICE_START)
public class StartServiceActivity extends AppCompatActivity {

    private ActivityStartServiceBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartServiceActivity.this, LifeCycleService.class);
                startService(intent);
                Logger.d(NameUtil.getName(StartServiceActivity.this) + "：startService()");

            }
        });

        binding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartServiceActivity.this, LifeCycleService.class);
                stopService(intent);
                Logger.d(NameUtil.getName(StartServiceActivity.this) + "：stopService()");

            }
        });
    }
}
