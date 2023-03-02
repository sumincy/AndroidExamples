package com.yung.android.basic.activity.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yung.android.basic.databinding.ActivityCommonProviderBinding;
import com.yung.android.basic.model.CustomerEntity;
import com.yung.android.basic.provider.MyContentProvider;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/02
 *    desc    :
 *
 *    ContentProvider（内容提供者）是 Android 的四大组件之一，
 *    管理 Android 以结构化方式存放的数据，以相对安全的方式封装数据（表）
 *    并且提供简易的处理机制和统一的访问接口供其他程序调用,
 *    注意：ContentProvider 的作用不是实现进程间通信，它只是为进程间通信提供了一套统一接口，
 *    真正实现进程间通信的是底层的 Binder 机制。
 *
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_COMMON_PROVIDER)
public class CommonProviderActivity extends AppCompatActivity {

    private ActivityCommonProviderBinding binding;

    private ContentResolver resolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonProviderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resolver = getContentResolver();

        initViews();
    }

    private void initViews() {
        Uri uri = Uri.parse(MyContentProvider.OPEN_URI);

        Random r = new Random();

        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put("name", "name-" + System.currentTimeMillis());
                values.put("age", r.nextInt(5) + 11);
                resolver.insert(uri, values);

                Logger.d(NameUtil.getName(CommonProviderActivity.this) + "：insert()：" + values);

            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = resolver.delete(uri, null, null);
                Logger.d(NameUtil.getName(CommonProviderActivity.this) + "：delete() 删除数据条数：" + id);
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(NameUtil.getName(CommonProviderActivity.this) + "：修改年龄=15的数据名称");


                ContentValues values = new ContentValues();
                values.put("name", "name_age_15");
                int id = resolver.update(uri, values, " AGE = ? ", new String[]{"15"});
                Logger.d(NameUtil.getName(CommonProviderActivity.this) + "：update() 更新数据条数：" + id);

            }
        });

        binding.btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = resolver.query(uri, new String[]{"id", "name", "age"}, null, null, null);

                List<CustomerEntity> customers = new ArrayList<>();

                while (cursor.moveToNext()) {
                    CustomerEntity entity = new CustomerEntity();

                    int idIndex = (cursor.getColumnIndex("id"));
                    if (idIndex >= 0) {
                        entity.setId(cursor.getInt(idIndex));
                    }
                    int nameIndex = (cursor.getColumnIndex("name"));

                    if (nameIndex >= 0) {
                        entity.setName(cursor.getString(nameIndex));
                    }

                    int ageIndex = (cursor.getColumnIndex("age"));
                    if (ageIndex >= 0) {
                        entity.setAge(cursor.getInt(ageIndex));
                    }
                    customers.add(entity);

                }
                cursor.close();

                Logger.d(NameUtil.getName(CommonProviderActivity.this) + "：query()：" + customers);

            }
        });


    }
}
