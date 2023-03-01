package com.yung.android.basic.activity.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.yung.android.basic.activity.LifecycleActivity;
import com.yung.android.basic.databinding.ActivitySingleInstanceBinding;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class SingleInstanceActivity extends LifecycleActivity {

    private final String TAG = this.getClass().getSimpleName();
    private ActivitySingleInstanceBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingleInstanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        binding.btnJumpStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleInstanceActivity.this, StandardActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingletop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleInstanceActivity.this, SingleTopActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingleTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleInstanceActivity.this, SingleTaskActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingleInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleInstanceActivity.this, SingleInstanceActivity.class);
                startActivity(intent);
            }
        });

        binding.btnPrintStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                printStack();

                //TODO Singleinstance 独占一个栈 不适用
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
