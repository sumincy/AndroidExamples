package com.yung.android.basic.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yung.android.basic.databinding.ActivityExamplesBinding;
import com.yung.android.common.app.CommonApplication;
import com.yung.android.common.entity.PageItem;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.adapter.PageItemsAdapter;
import com.yung.android.common.util.AssetsUtil;

import java.util.List;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    : activity 使用
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_EXAMPLES)
public class ActivityExamplesActivity extends AppCompatActivity {

    private ActivityExamplesBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExamplesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ((CommonApplication) getApplication()).getLogCache().setLength(0);

        initViews();
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvActivityExamples.setLayoutManager(linearLayoutManager);

        List<PageItem> pageItems = PageItem.getPageItems(getApplicationContext(), "activity_example.json");

        PageItemsAdapter pageItemsAdapter = new PageItemsAdapter(this, pageItems);

        binding.rcvActivityExamples.setAdapter(pageItemsAdapter);

    }
}
