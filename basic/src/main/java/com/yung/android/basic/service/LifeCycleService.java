package com.yung.android.basic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    :
 *
 *    Service 是一个可以在后台执行长时间运行操作而不提供用户界面的应用组件。
 *    服务可由其他应用组件启动，而且即使用户切换到其他应用，服务仍将在后台继续运行。
 *    此外，组件可以绑定到服务，以与之进行交互，甚至是执行进程间通信 (IPC)。
 *    例如，服务可以处理网络事务、播放音乐，执行文件 I/O 或与内容提供程序交互，而所有这一切均可在后台进行。
 *
 *
 *    version : 1.0
 * <pre>
 */
public class LifeCycleService extends Service {

    private String name;

    private Thread thread;
    private boolean quit;
    private int count;

    IBinder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e(NameUtil.getName(this) + "：onCreate()");

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 每间隔一秒count加1 ，直到quit为true。
                while (!quit) {
                    count++;

                    try {
                        Thread.sleep(1000);
                        Logger.e(NameUtil.getName( LifeCycleService.this) + "--" + NameUtil.getName(thread) + "：run()  count：" + count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        thread.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.e(NameUtil.getName(this) + "：onBind()");
        mBinder = new MyBinder();
        name = "Bind";

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.e(NameUtil.getName(this) + "：onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.e(NameUtil.getName(this) + "：onStartCommand()");
        name = "Start";

        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends Binder {
        public void doSometing() {
            Logger.e(NameUtil.getName(this) + "：doSometing()");
        }

        public LifeCycleService getService() {
            return LifeCycleService.this;
        }
    }

    @Override
    public void onDestroy() {
        this.quit = true;
        super.onDestroy();
        Logger.e(NameUtil.getName(this) + "：onDestroy()");
    }
}
