package com.yung.android.task.ui.activity.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
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
import com.yung.android.task.databinding.ActivityCommonHandlerBinding;
import com.yung.android.task.databinding.ActivityHandlerThreadBinding;

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
@Route(path = PagePath.ACTIVITY_HANDLER_THREAD)
public class HandlerThreadActivity extends AppCompatActivity {

    private String id = UUID.randomUUID().toString();

    private final String TAG = this.getClass().getName();

    private ActivityHandlerThreadBinding binding;

    private HandlerThread handlerThread = new HandlerThread("handlerThread");


    private static final int MSG_EMPTY = 100;

    private static final int MSG_SIMPLE = 101;


    private static final int MSG_DELAY = 102;

    private boolean quit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHandlerThreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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


        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper(), new CallBack());

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(MSG_EMPTY);
            }
        });

        binding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit = true;
            }
        });

    }

    class CallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(@NonNull Message msg) {

            //子线程内做耗时任务
            if (MSG_EMPTY == msg.what) {
                quit = false;
                Logger.e(NameUtil.getName(CallBack.this) + "：handleMessage()：empty");
            }

            int count = 0;

            while (!quit) {
                try {
                    Thread.sleep(1000);
                    Logger.e(NameUtil.getName(CallBack.this) + "：" + count++);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


            return false;
        }
    }

    @Override
    protected void onDestroy() {
        this.quit = true;
        super.onDestroy();
    }
}
