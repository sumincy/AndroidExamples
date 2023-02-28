package com.yung.android.basic.activity.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yung.android.basic.databinding.ActivityLaunchModeBinding;
import com.yung.android.common.entity.PagePath;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    :
 *
 *    LaunchMode
 *    Standard:该启动模式下,多次启动一个activity 会创建多个实例，返回时也是逐个返回 销毁
 *    SingleTop ：当该启动模式的Activity 在栈顶时，再次启动会调用 onNewIntent  不在栈顶时，与Standard模式相同 栈内可存在多个
 *    SingleTask: 该启动模式的activity在栈顶时，再次启动，会调用 onNewIntent 不在栈顶时 会结束其之上的所有activity 使其置于栈顶 并调用onNewIntent  一个task内只有一个
 *
 *    SingleInstance : activity 独享一个栈
 *
 *    A(Standard)-B(SingleInstance)-A(Standard)-返回
 *    此情况下不会返回B 而是返回到A  再次跳转B 会调用B的onNewIntent
 *
 *    B(SingleInstance)-B(SingleInstance)
 *    会调用B的onNewIntent
 *
 *    A(Standard)-B(SingleInstance)-A(Standard)-B(SingleInstance)
 *    会调用B的onNewIntent
 *
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_LAUNCH_MODE)
public class LaunchModeActivity extends AppCompatActivity {

    private ActivityLaunchModeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLaunchModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        binding.btnStandrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LaunchModeActivity.this, StandardActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSingletop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchModeActivity.this, SingleTopActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSingleTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchModeActivity.this, SingleTaskActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSingleInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchModeActivity.this, SingleInstanceActivity.class);
                startActivity(intent);
            }
        });
    }
}
