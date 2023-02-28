package com.yung.android.basic.activity.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.yung.android.basic.databinding.ActivitySingleTopBinding;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class SingleTopActivity extends StackActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ActivitySingleTopBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "------onCreate------" + TAG);

        binding = ActivitySingleTopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "------onNewIntent------" + TAG);
    }

    private void initViews() {

        binding.btnJumpStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTopActivity.this, StandardActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingletop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTopActivity.this, SingleTopActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingleTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTopActivity.this, SingleTaskActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingleInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTopActivity.this, SingleInstanceActivity.class);
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
