package com.yung.android.basic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yung.android.basic.activity.broadcast.StaticBroadcastActivity;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Logger.e(NameUtil.getName(this) + "：onReceive() 收到广播");

        String msg = "sender：[" + intent.getStringExtra("sender") + "],action：[" + intent.getAction() + "]";

        Logger.e(msg);
    }
}
