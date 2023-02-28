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
import com.yung.android.basic.databinding.ActivityCommonLifecycleBinding;
import com.yung.android.basic.databinding.ActivityConfigchangesLifecycleBinding;
import com.yung.android.common.app.CommonApplication;
import com.yung.android.common.entity.PagePath;

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
public class ConfigChangeLifeCylcleActivity extends AppCompatActivity {

    private String id = UUID.randomUUID().toString();

    private final String TAG = this.getClass().getName();

    private ActivityConfigchangesLifecycleBinding binding;

    private StringBuilder sb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "------onCreate------" + id);
        binding = ActivityConfigchangesLifecycleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        sb = ((CommonApplication) getApplication()).getLogCache();

        sb.append("onCreate()");
        sb.append(System.getProperty("line.separator"));
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
        Log.i(TAG, "------onSaveInstanceState------" + id);

        sb.append("onSaveInstanceState()");
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "------onRestoreInstanceState------" + id);

        sb.append("onRestoreInstanceState()");
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "------onStart------" + id);

        sb.append("onStart()");
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "------onRestart------" + id);

        sb.append("onStart()");
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "------onConfigurationChanged------" + id);

        sb.append("onConfigurationChanged()");
        sb.append(System.getProperty("line.separator"));

        binding.tvLog.setText(sb.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "------onResume------" + id);

        sb.append("onResume()");
        sb.append(System.getProperty("line.separator"));

        binding.tvLog.setText(sb.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "------onPause------" + id);

        sb.append("onPause()");
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "------onStop------" + id);

        sb.append("onStop()");
        sb.append(System.getProperty("line.separator"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "------onDestroy------" + id);

        sb.append("onDestroy()");
        sb.append(System.getProperty("line.separator"));
    }
}
