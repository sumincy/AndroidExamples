package com.yung.android.basic.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.yung.android.basic.databinding.ActivityExamplesBinding;
import com.yung.android.common.entity.PageItem;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.adapter.PageItemsAdapter;
import com.yung.android.common.ui.wiget.Logger;

import java.util.List;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    : examplse
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_EXAMPLES)
public class ExamplesActivity extends AppCompatActivity {

    private ActivityExamplesBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExamplesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvActivityExamples.setLayoutManager(linearLayoutManager);

        String jsonFile = getIntent().getStringExtra("list_json");

        List<PageItem> pageItems = PageItem.getPageItems(getApplicationContext(), jsonFile);

        PageItemsAdapter pageItemsAdapter = new PageItemsAdapter(this, pageItems);

        binding.rcvActivityExamples.setAdapter(pageItemsAdapter);


        EasyFloat.with(this).setLayout(com.yung.android.common.R.layout.layout_float_button, new OnInvokeView() {
                    @Override
                    public void invoke(View view) {
                        view.findViewById(com.yung.android.common.R.id.tv_log).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Logger.getInstance().loggerSwitch();
                            }
                        });
                    }
                })
                .setGravity(Gravity.TOP | Gravity.RIGHT)
                .setShowPattern(ShowPattern.ALL_TIME)
                .show();
    }

}
