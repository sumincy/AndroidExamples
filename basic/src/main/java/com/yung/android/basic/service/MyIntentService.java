package com.yung.android.basic.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/02
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class MyIntentService extends IntentService {

    private int count;
    private boolean quit;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e(NameUtil.getName(this) + "：onCreate()");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Logger.e(NameUtil.getName(this) + "：onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Logger.e(NameUtil.getName(this) + "：onHandleIntent()");

        String msg = intent.getExtras().getString("msg");
        Logger.e(NameUtil.getName(this) + "：onHandleIntent()：" + msg);

        while (!quit) {
            count++;

            try {
                Thread.sleep(1000);
                Logger.e(NameUtil.getName(MyIntentService.this) + "--" + "count：" + count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        Logger.e(NameUtil.getName(this) + "：onDestroy()");
    }
}
