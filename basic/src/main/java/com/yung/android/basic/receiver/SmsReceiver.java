package com.yung.android.basic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/02
 *    desc    : 短信接受
 *    version : 1.0
 * <pre>
 */
public class SmsReceiver extends BroadcastReceiver {
    public final String TAG = "SmsReceiver";
    public SmsMessage[] messages;
    public String temp = null;
    public String body;

    public interface ReceivdeSms {
        //void smsMsg(SmsMessage sms, int messagesLength);
        void smsMsg(long date, String tel, String name, String body);
    }

    public ReceivdeSms receivdeSms;

    public void setReceivdeSms(ReceivdeSms receivdeSms) {
        this.receivdeSms = receivdeSms;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(action)) {
            Logger.i(NameUtil.getName(SmsReceiver.this) + "onReceive()：SMS_RECEIVED 收到短信");

            try {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    messages = new SmsMessage[pdus.length];  //解析短信
                    for (int i = 0; i < pdus.length; i++) {
                        byte[] pdu = (byte[]) pdus[i];
                        messages[i] = SmsMessage.createFromPdu(pdu);
                    }
                }

                SmsMessage sms = messages[0];
                //输出参数内容
                Date date = new Date(sms.getTimestampMillis());  // 获取短信日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                String receiveTime = sdf.format(date);
                //日期
                String data = receiveTime.trim();
                //电话号码
                temp = sms.getOriginatingAddress();
                //Email姓名 没有为 null
                //String emailName = sms.getEmailBody();
                String emailName = null;
                if (sms.isEmail()) {
                    emailName = sms.getEmailFrom();
                }

                //文本
                if (messages.length == 1) {
                    body = sms.getDisplayMessageBody();
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < messages.length; i++) {
                        sms = messages[i];
                        sb.append(sms.getDisplayMessageBody());
                    }
                    body = body.toString();
                }
                //状态码
                int status = sms.getStatus();
                //服务中心中转号码
                String centerAddress = sms.getServiceCenterAddress();

                if (receivdeSms != null) {
                    receivdeSms.smsMsg(sms.getTimestampMillis(), temp, emailName, body);
                }

                Logger.e(NameUtil.getName(SmsReceiver.this) + "时间: " + data +
                        ", 电话：" + temp +
                        ", 内容：" + body +
                        ", 状态码：" + status +
                        ", 服务中心转发号码：" + centerAddress);

                //abortBroadcast();     // 终止广播接收
            } catch (Exception e) {
                Logger.e(NameUtil.getName(SmsReceiver.this) + "：SmsReceiver onReceive Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
