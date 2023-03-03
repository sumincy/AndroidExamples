package com.yung.android.task.ui.activity.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;
import com.yung.android.task.databinding.ActivityCommonHandlerBinding;

import java.util.UUID;

/**
 * <pre>
 *
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/02
 *    desc    :  handler
 *    version : 1.0
 *
 *   创建Handler，并采用当前线程的Looper创建消息循环系统；
 * Handler通过sendMessage(Message)或Post(Runnable)发送消息，
 * 调用enqueueMessage把消息插入到消息链表中；
 * Looper循环检测消息队列中的消息，若有消息则取出该消息，
 * 并调用该消息持有的handler的dispatchMessage方法，
 * 回调到创建Handler线程中重写的handleMessage里执行。
 *
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_COMMON_HANDLER)
public class CommonHandlerActivity extends AppCompatActivity {

    private String id = UUID.randomUUID().toString();

    private final String TAG = this.getClass().getName();

    private ActivityCommonHandlerBinding binding;

    private Handler handler = new MyHandler();

    private static final int MSG_EMPTY = 100;

    private static final int MSG_SIMPLE = 101;


    private static final int MSG_DELAY = 102;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommonHandlerBinding.inflate(getLayoutInflater());
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

        binding.btnSendEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(MSG_EMPTY);
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = MSG_SIMPLE;
                message.obj = "simple msg";
                handler.sendMessage(message);
            }
        });

        binding.btnSendDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = MSG_DELAY;
                message.obj = "delay msg";
                handler.sendMessageDelayed(message, 3000);
            }
        });
    }


    private static class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (MSG_EMPTY == msg.what) {
                Logger.e(NameUtil.getName(MyHandler.this) + "：handleMessage()：empty");
            } else if (MSG_SIMPLE == msg.what) {
                Logger.e(NameUtil.getName(MyHandler.this) + "：handleMessage()：" + msg.obj);
            } else if (MSG_DELAY == msg.what) {
                Logger.e(NameUtil.getName(MyHandler.this) + "：handleMessage()：" + msg.obj);
            }


        }
    }

}
