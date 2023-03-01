package com.yung.android.basic.activity.lifecycle;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yung.android.basic.activity.LifecycleActivity;
import com.yung.android.basic.databinding.ActivityCommonLifecycleBinding;
import com.yung.android.basic.databinding.ActivityConfigchangesLifecycleBinding;
import com.yung.android.common.app.CommonApplication;
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
 *   Activity横竖屏生命周期  AndroidManifest.xml中设置configChanges
 *
 *  运行-竖屏-横屏-竖屏
 * configChanges=orientation
 * 运行：onCreate-onStart-onResume
 * 竖屏-横屏：onPause-onStop-onSaveInstanceState-onDestroy-onCreate-onStart-onRestoreInstanceState-onResume
 * 横屏-竖屏：onPause-onStop-onSaveInstanceState-onDestroy-onCreate-onStart-onRestoreInstanceState-onResume
 *
 * configChanges=orientation|keyboardHidden|screenSize
 * 运行：onCreate-onStart-onResume
 * 竖屏-横屏： onConfigurationChanged
 * 横屏-竖屏： onConfigurationChanged
 *
 *
 * 设置Activity的android:configChanges属性为orientation或者orientation|keyboardHidden或者不设置这个属性的时候
 * 横竖屏切换会重新调用各个生命周期方法，切横屏时会执行1次，切竖屏时会执行1次；
 * 设置Activity的属性为android:configChanges="orientation|keyboardHidden|screenSize"时
 * 横竖屏切换不会重新调用各个生命周期方法，只会执行onConfigurationChanged方法；
 *
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_CONFIG_CHANGE_LIFECYCLE)
public class ConfigChangeLifeCylcleActivity extends LifecycleActivity {

    private String id = UUID.randomUUID().toString();

    private final String TAG = this.getClass().getName();

    private ActivityConfigchangesLifecycleBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("------onCreate()------" + this);
        binding = ActivityConfigchangesLifecycleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        binding.btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(PagePath.ACTIVITY_CONFIG_CHANGE_LIFECYCLE)
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.i("------onSaveInstanceState()------" + this);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.i("------onRestoreInstanceState()------" + this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i("------onStart()------" + this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.i("------onRestart()------" + this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.i("------onConfigurationChanged()------" + this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("------onResume()------" + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i("------onPause()------" + this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i("------onStop()------" + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i("------onDestroy()------" + this);
    }
}
