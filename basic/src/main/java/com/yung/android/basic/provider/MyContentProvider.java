package com.yung.android.basic.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.yung.android.basic.database.CustomerDao;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/02
 *    desc    :
 *
 *
 *    version : 1.0
 * <pre>
 */
public class MyContentProvider extends ContentProvider {

    private Context mContext;
    private CustomerDao customerDao;

    public static final String OPEN_URI = "content://yung.android.provider/customer";


    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        customerDao = new CustomerDao(mContext);
        return false;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        return customerDao.delete(selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return customerDao.insert(values);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return customerDao.query(projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return customerDao.update(values, selection, selectionArgs);
    }
}