package com.yung.android.basic.activity.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.yung.android.basic.databinding.ActivityBindServiceBinding;
import com.yung.android.basic.service.LifeCycleService;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    : BindService
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_SERVICE_BIND)
public class BindServiceActivity extends AppCompatActivity {

    private ActivityBindServiceBinding binding;

    private MyConnection myConnection;

    private boolean isBind = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBindServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        myConnection = new MyConnection();

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

        binding.btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null == myConnection) {
                    myConnection = new MyConnection();
                }

                Intent intent = new Intent(BindServiceActivity.this, LifeCycleService.class);
                isBind = bindService(intent, myConnection, Service.BIND_AUTO_CREATE);

                Logger.d(NameUtil.getName(BindServiceActivity.this) + "：bindService()  isBind：" + isBind);
            }
        });

        binding.btnUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != myConnection && isBind) {
                    unbindService(myConnection);
                    myConnection = null;
                    isBind = false;
                }

                Logger.d(NameUtil.getName(BindServiceActivity.this) + "：unbindService()  isBind：" + isBind);
            }
        });
    }

    public class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Logger.e(NameUtil.getName(this) + "：onServiceConnected()");
            ((LifeCycleService.MyBinder) binder).doSometing();

            ((LifeCycleService.MyBinder) binder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.e(NameUtil.getName(this) + "：onServiceDisconnected()");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myConnection && isBind) {
            unbindService(myConnection);
            Logger.e(NameUtil.getName(this) + "：unbindService()  isBind：" + isBind);
        }
    }
}
