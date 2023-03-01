package com.yung.android.basic.activity.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yung.android.basic.databinding.ActivityDynamicBroadcastReceiverBinding;
import com.yung.android.basic.databinding.ActivityOrderedBroadcastReceiverBinding;
import com.yung.android.basic.receiver.HighLevelBroadCastReceiver;
import com.yung.android.basic.receiver.MyBroadCastReceiver;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;
import com.yung.android.common.util.TimeUtil;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    :
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_BROADCAST_ORDERED)
public class OrderedBroadCastActivity extends AppCompatActivity {

    private ActivityOrderedBroadcastReceiverBinding binding;

    private static final String ORDERED_ACTION = "com.yung.android.example.orderedbroadcast";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderedBroadcastReceiverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {

        binding.btnSendOrdered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(ORDERED_ACTION);
                intent.putExtra("sender", NameUtil.getName(OrderedBroadCastActivity.this) + " time：" + TimeUtil.getTime());
                intent.putExtra("msg", "1+1=2");
                intent.setPackage(getPackageName());

//                intent.setClassName(getPackageName(), MyBroadCastReceiver.class.getName());
                sendOrderedBroadcast(intent, null);
                Logger.d(NameUtil.getName(OrderedBroadCastActivity.this) + "：sendOrderedBroadcast");
            }
        });
    }

}
