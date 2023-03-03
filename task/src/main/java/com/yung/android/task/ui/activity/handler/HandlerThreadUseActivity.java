package com.yung.android.task.ui.activity.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;
import com.yung.android.task.databinding.ActivityHandlerThreadBinding;
import com.yung.android.task.databinding.ActivityHandlerThreadUseBinding;

import java.util.UUID;

/**
 * <pre>
 *
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/02
 *    desc    :  handler Thread
 *    version : 1.0
 *
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_HANDLER_THREAD_USE)
public class HandlerThreadUseActivity extends AppCompatActivity {


    private final String TAG = this.getClass().getName();

    private ActivityHandlerThreadUseBinding binding;

    private CallThread callThread;

    private static final int MSG_EMPTY = 100;

    private boolean quit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHandlerThreadUseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        callThread = new CallThread();
        callThread.start();

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


        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callThread.mHandler.sendEmptyMessage(MSG_EMPTY);
            }
        });

        binding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit = true;
                Logger.e(NameUtil.getName(HandlerThreadUseActivity.this) + "：stop");
            }
        });

    }

    class CallThread extends Thread {

        private Handler mHandler;

        @Override
        public void run() {
            super.run();
            Looper.prepare();
//            mHandler = new Handler(new Handler.Callback() {
//                @Override
//                public boolean handleMessage(@NonNull Message msg) {
//
//                    //子线程内做耗时任务
//                    if (MSG_EMPTY == msg.what) {
//                        quit = false;
//                        Logger.e(NameUtil.getName(CallThread.this) + "：handleMessage()：empty");
//                    }
//
//                    int count = 0;
//
//                    while (!quit) {
//                        try {
//                            Thread.sleep(1000);
//                            Logger.e(NameUtil.getName(CallThread.this) + "：" + count++);
//
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//
//                    return false;
//                }
//            });

            mHandler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    //子线程内做耗时任务
                    if (MSG_EMPTY == msg.what) {
                        quit = false;
                        Logger.e(NameUtil.getName(CallThread.this) + "：handleMessage()：empty");
                    }

                    int count = 0;

                    while (!quit) {
                        try {
                            Thread.sleep(1000);
                            Logger.e(NameUtil.getName(CallThread.this) + "：" + count++);

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            };

            Looper.loop();
        }

    }

    @Override
    protected void onDestroy() {
        this.quit = true;
        super.onDestroy();
    }
}
