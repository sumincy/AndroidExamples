package com.yung.android.common.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.yung.android.common.databinding.ActivityListBinding;
import com.yung.android.common.entity.PageItem;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.adapter.PageItemsAdapter;
import com.yung.android.common.ui.wiget.Logger;

import java.util.List;

@Route(path = PagePath.ACTIVITY_LIST)
public class ListActivity extends AppCompatActivity {

    private ActivityListBinding binding;
    private PageItemsAdapter listAdapter;

    private final String TAG = "examples";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {

        String title = getIntent().getStringExtra("name");
        binding.titleBar.setTitle(title);
        
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvMain.setLayoutManager(linearLayoutManager);


        String jsonFile = getIntent().getStringExtra("list_json");

        List<PageItem> pageItems = PageItem.getPageItems(getApplicationContext(), jsonFile);

        listAdapter = new PageItemsAdapter(this, pageItems);
        binding.rcvMain.setAdapter(listAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}