package com.yung.android.basic.activity.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.yung.android.basic.activity.LifecycleActivity;
import com.yung.android.basic.databinding.ActivityStandradBinding;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class StandardActivity extends LifecycleActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ActivityStandradBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStandradBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews() {

        binding.btnJumpStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandardActivity.this, StandardActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingletop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandardActivity.this, SingleTopActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingleTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandardActivity.this, SingleTaskActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingleInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandardActivity.this, SingleInstanceActivity.class);
                startActivity(intent);
            }
        });


        binding.btnPrintStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printStack();
            }
        });

        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
