package com.yung.android.basic.activity.lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yung.android.basic.activity.LifecycleActivity;
import com.yung.android.basic.databinding.ActivityCommonLifecycleBinding;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;

import java.util.UUID;

/**
 * <pre>
 *
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    : andorid activity 生命周期
 *    version : 1.0
 *
 * 启动状态（Starting）：Activity的启动状态很短暂，当Activity启动后便会进入运行状态（Running）。
 * 运行状态（Running）：Activity在此状态时处于屏幕最前端，它是可见、有焦点的，可以与用户进行交互。如单击、长按等事件。即使出现内存不足的情况，Android也会先销毁栈底的Activity，来确保当前的Activity正常运行。
 * 暂停状态（Paused）：在某些情况下，Activity对用户来说仍然可见，但它无法获取焦点，用户对它操作没有没有响应，此时它处于暂停状态。
 * 停止状态（Stopped）：当Activity完全不可见时，它处于停止状态，但仍然保留着当前的状态和成员信息。如系统内存不足，那么这种状态下的Activity很容易被销毁。
 * 销毁状态（Destroyed）：当Activity处于销毁状态时，将被清理出内存。
 *
 * onCreate() ： 在Activity创建时调用，通常做一些初始化设置；
 * onStart()： 在Activity即将可见时调用；
 * onResume()： 在Activity已可见，获取焦点开始与用户交互时调用；
 * onPause()： 在当前Activity被其他Activity覆盖或锁屏时调用；
 * onStop() ： 在Activity对用户不可见时调用；
 * onDestroy() ：在Activity销毁时调用；
 * onRestart() ： 在Activity从停止状态再次启动时调用；
 *
 * A-返回
 * onCreate()->onStart()->onResume()->onPause()->onStop()->onDestory()
 *
 * A-B-返回A
 * onCreate()->onStart()->onResume()->onPause()->onStop()-onRestart()-onStart()-onResume()
 *
 *
 * Activity横竖屏生命周期  AndroidManifest.xml中未设置configChanges
 * 运行-竖屏-横屏
 * 运行：onCreate-onStart-onResume
 * 竖屏-横屏：onPause-onStop-onSaveInstanceState-onDestroy-onCreate-onStart-onRestoreInstanceState-onResume
 *
 *
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_COMMON_LIFECYCLE)
public class CommonLifeCylcleActivity extends LifecycleActivity {

    private String id = UUID.randomUUID().toString();

    private final String TAG = this.getClass().getName();

    private ActivityCommonLifecycleBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommonLifecycleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        binding.btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(PagePath.ACTIVITY_COMMON_LIFECYCLE)
                        .navigation();
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
