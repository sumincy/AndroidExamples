package com.yung.android.examples.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.yung.android.common.entity.PageItem;
import com.yung.android.common.ui.adapter.PageItemsAdapter;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.examples.R;
import com.yung.android.examples.databinding.ActivityMainBinding;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PageItemsAdapter mainListAdapter;

    private final String TAG = "examples";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvMain.setLayoutManager(linearLayoutManager);

        List<PageItem> pageItems = PageItem.getPageItems(getApplicationContext(), "main_example.json");

        mainListAdapter = new PageItemsAdapter(this, pageItems);
        binding.rcvMain.setAdapter(mainListAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Logger.getInstance().hideLogger();
    }
}