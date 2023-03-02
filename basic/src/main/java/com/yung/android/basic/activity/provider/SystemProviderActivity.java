package com.yung.android.basic.activity.provider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 *    在国内那些定制了Android系统的手机是读取不到通知类或者服务类短信的，三星和google的手机是可以读取到的。
 *    国内的那些定制的手机需要这样操作：“信息设置--验证码安全保护关闭”。
 *
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

    private String[] sms_permissions = new String[]{
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private List<String> refusedList;

    private final int REQUEST_CONTACTS_CODE = 100;

    private final int REQUEST_CALL_LOG_CODE = 101;

    private final int REQUEST_SMS_CODE = 102;


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
                    ActivityCompat.requestPermissions(SystemProviderActivity.this, refusedList.toArray(new String[refusedList.size()]), REQUEST_CONTACTS_CODE);
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
                    ActivityCompat.requestPermissions(SystemProviderActivity.this, refusedList.toArray(new String[refusedList.size()]), REQUEST_CALL_LOG_CODE);
                }
            }
        });


        binding.btnGetSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：getSms()");
                refusedList = null;

                checkPermission(sms_permissions);

                if (refusedList == null || refusedList.size() == 0) {
                    getSms();
                } else {
                    ActivityCompat.requestPermissions(SystemProviderActivity.this, refusedList.toArray(new String[refusedList.size()]), REQUEST_SMS_CODE);
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

    /**
     * 获取短信
     * content://sms/          所有短信
     * content://sms/inbox     收件箱
     * content://sms/sent      已发送
     * content://sms/draft     草稿
     * content://sms/outbox    发件箱
     * content://sms/failed    发送失败
     * content://sms/queued    待发送列表
     */
    public void getSms() {

//        Uri uri = Telephony.Sms.Inbox.CONTENT_URI;

        final String SMS_URI_ALL = "content://sms/"; // 所有短信

        StringBuilder smsBuilder = new StringBuilder();

        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type",};
            Cursor cur = getContentResolver().query(uri, projection, null,
                    null, Telephony.Sms.DEFAULT_SORT_ORDER); // 获取手机内部短信

            // 获取短信中最新的未读短信
            // Cursor cur = getContentResolver().query(uri, projection,
            // "read = ?", new String[]{"0"}, "date desc");

            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");

                do {
                    String strAddress = cur.getString(index_Address);
                    int intPerson = cur.getInt(index_Person);
                    String strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int intType = cur.getInt(index_Type);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(longDate);
                    String strDate = dateFormat.format(d);

                    String strType = "";
                    if (intType == 1) {
                        strType = "接收";
                    } else if (intType == 2) {
                        strType = "发送";
                    } else if (intType == 3) {
                        strType = "草稿";
                    } else if (intType == 4) {
                        strType = "发件箱";
                    } else if (intType == 5) {
                        strType = "发送失败";
                    } else if (intType == 6) {
                        strType = "待发送列表";
                    } else if (intType == 0) {
                        strType = "所有短信";
                    } else {
                        strType = "null";
                    }

                    smsBuilder.append("[ ");
                    smsBuilder.append(strAddress + ", ");
                    smsBuilder.append(intPerson + ", ");
                    smsBuilder.append(strbody + ", ");
                    smsBuilder.append(strDate + ", ");
                    smsBuilder.append(strType);
                    smsBuilder.append(" ]");
                    //换行
                    smsBuilder.append(System.getProperty("line.separator"));

                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                smsBuilder.append("no result!");
            }

        } catch (SQLiteException ex) {
            Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
        }

        Logger.d("---->" + smsBuilder.toString());

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：onRequestPermissionsResult()：" + grantResults);

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
        } else if (requestCode == REQUEST_SMS_CODE) {
            if (hasAllPermissionGranted(permissions, grantResults)) {
                Logger.d(NameUtil.getName(SystemProviderActivity.this) + "：onRequestPermissionsResult()：" + "用户允许了所有授权");
                getSms();
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

