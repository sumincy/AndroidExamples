package com.yung.android.basic.activity.provider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yung.android.basic.databinding.ActivitySystemProviderBinding;
import com.yung.android.basic.model.ContactEntity;
import com.yung.android.common.entity.PagePath;
import com.yung.android.common.ui.wiget.Logger;
import com.yung.android.common.util.NameUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/02
 *
 *    desc    :
 *    通过ContentProvider
 *    获取手机联系人
 *    获取通话记录
 *
 *    version : 1.0
 * <pre>
 */
@Route(path = PagePath.ACTIVITY_SYSTEM_PROVIDER)
public class SystemProviderActivity extends AppCompatActivity {

    private ActivitySystemProviderBinding binding;

    private String[] contact_permissions = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };

    private String[] call_log_permissions = new String[]{
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG
    };

    private List<String> refusedList;

    private final int REQUEST_CONTACTS_CODE = 100;

    private final int REQUEST_CALL_LOG_CODE = 101;


    /**
     * 找寻到了Android操作系统的通话记录内容提供者的授权和Uri
     * android:authorities="call_log"
     * sURIMatcher.addURI(CallLog.AUTHORITY, "calls", CALLS);
     */
    private final String AUTHORITY = "call_log";
    private Uri callLogUri = Uri.parse("content://" + AUTHORITY + "/calls");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySystemProviderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        binding.btnGetContatcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：getContacts()");
                refusedList = null;

                checkPermission(contact_permissions);

                if (refusedList == null || refusedList.size() == 0) {
                    getContacts();
                } else {
                    ActivityCompat.requestPermissions(SystemProviderActivity.this,  refusedList.toArray(new String[refusedList.size()]), REQUEST_CONTACTS_CODE);
                }
            }
        });

        binding.btnGetCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：getContacts()");
                refusedList = null;

                checkPermission(call_log_permissions);

                if (refusedList == null || refusedList.size() == 0) {
                    getCallLog();
                } else {
                    ActivityCompat.requestPermissions(SystemProviderActivity.this,refusedList.toArray(new String[refusedList.size()]), REQUEST_CALL_LOG_CODE);
                }
            }
        });
    }

    /**
     * 获取联系人
     */
    private void getContacts() {

        List<ContactEntity> contacts = new ArrayList<>();

        //2个关键的URI
//        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        //1.去raw_contacts获取联系人的_id
        Cursor query = getContentResolver().query(uri, new String[]{"_id"}, null, null, null);
        if (query != null && query.getCount() > 0) {

            while (query.moveToNext()) {
                ContactEntity contactEntity = new ContactEntity();

                //从cursor结果集的第一位得到id
                int id = query.getInt(0);

                //SQL查询语句    select * from raw_contact where _id = ?
                String selection = "raw_contact_id = ?";
                String[] selectionArgs = {String.valueOf(id)};

                //查询结果集cursor
                Cursor cursor = getContentResolver().query(dataUri, new String[]{"data1", "mimetype"},
                        selection, selectionArgs, null);
                if (cursor != null && cursor.getCount() > 0) {
                    //从cursor中取出我们需要的联系人姓名和电话
                    while (cursor.moveToNext()) {
                        String data1 = cursor.getString(0);
                        String mimetype = cursor.getString(1);
                        String tag = "联系人：";

                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            contactEntity.setPhone(data1);
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            contactEntity.setName(data1);
                        }

                    }
                    cursor.close();
                }

                contacts.add(contactEntity);
            }
            query.close();

            Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：query()：" + contacts);
        }
    }

    public void getCallLog() {
        String[] porjecting = new String[]{"_id", "number", "date", "type"};
        final Cursor cursor = getContentResolver().query(callLogUri,
                porjecting,
                null, // 不要查询条件
                null, // 不要查询条件值
                null); // 不排序

        while (cursor.moveToNext()) {
            Logger.d("" + cursor.getString(0) + "  " + cursor.getString(1) + " " + cursor.getString(2) + "   " + cursor.getString(3));
        }

        cursor.close();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：onRequestPermissionsResult()：" + grantResults);

        if (requestCode == REQUEST_CONTACTS_CODE) {
            if (hasAllPermissionGranted(permissions, grantResults)) {
                Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：onRequestPermissionsResult()：" + "用户允许了所有授权");
                getContacts();
            }
        } else if (requestCode == REQUEST_CALL_LOG_CODE) {
            if (hasAllPermissionGranted(permissions, grantResults)) {
                Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：onRequestPermissionsResult()：" + "用户允许了所有授权");
                getCallLog();
            }
        }
    }


    /**
     * 检查权限
     *
     * @param permissions
     */
    public void checkPermission(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(getApplication(), permission)
                        == PackageManager.PERMISSION_DENIED) {
                    if (refusedList == null) {
                        refusedList = new ArrayList<>();
                    }
                    refusedList.add(permission);
                }
            }
        }

    }


    /**
     * 判断是否给与了所有权限
     *
     * @param permissions
     * @param grantResults
     * @return
     */
    public boolean hasAllPermissionGranted(String[] permissions, int[] grantResults) {

        for (int i = 0; i < grantResults.length; i++) {
            int result = grantResults[i];
            if (result == PackageManager.PERMISSION_DENIED) {
                Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：hasAllPermissionGranted()：" + permissions[i] + "：PERMISSION_DENIED");
                return false;
            }
        }

        return true;
    }
}

