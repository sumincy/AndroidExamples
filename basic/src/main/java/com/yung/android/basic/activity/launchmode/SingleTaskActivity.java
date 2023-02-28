package com.yung.android.basic.activity.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.yung.android.basic.databinding.ActivitySingleTaskBinding;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class SingleTaskActivity extends StackActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ActivitySingleTaskBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "------onCreate------" + TAG);

        binding = ActivitySingleTaskBinding.inflate(getLayoutInflater());
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
                Intent intent = new Intent(SingleTaskActivity.this, StandardActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingletop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTaskActivity.this, SingleTopActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingleTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTaskActivity.this, SingleTaskActivity.class);
                startActivity(intent);
            }
        });

        binding.btnJumpSingleInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTaskActivity.this, SingleInstanceActivity.class);
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
