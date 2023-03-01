package com.yung.android.basic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
public class HighLevelBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Logger.e(NameUtil.getName(this) + "：onReceive() 收到广播");

        String msg = "sender：[" + intent.getStringExtra("sender") + "],action：[" + intent.getAction() + "]";

        Logger.e(msg);

        // 1.abortBroadcast 开启后 middle lower将收不到
        abortBroadcast();
        Logger.e(NameUtil.getName(this) + "：abortBroadcast() 中断广播");

        //2.高级别广播修改 数据
        setResultData("A");
        Logger.d(NameUtil.getName(this) + "：setResultData()：A");

    }
}
