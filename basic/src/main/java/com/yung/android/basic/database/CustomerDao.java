package com.yung.android.basic.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.yung.android.basic.provider.MyContentProvider;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/02
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class CustomerDao {

    private SQLiteDatabase sqLiteDatabase;
    private Context mContext;

    private final String TABLE_NAME = "customer";

    public CustomerDao(Context context) {
        MyDBHelper dbHelper = new MyDBHelper(context, TABLE_NAME, null, 1);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        this.mContext = context;
    }


    /**
     * insert
     *
     * @param contentValues
     * @return
     */
    public Uri insert(ContentValues contentValues) {
        long rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Uri uri = Uri.parse(MyContentProvider.OPEN_URI);

        Uri insertUri = ContentUris.withAppendedId(uri, rowId);
        mContext.getContentResolver().notifyChange(insertUri, null);
        return insertUri;
    }

    /**
     * 修改
     *
     * @param contentValues
     * @param selection
     * @param selectionArgs
     * @return
     */
    public int update(ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        return sqLiteDatabase.update(TABLE_NAME, contentValues, selection, selectionArgs);
    }

    /**
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return SQLiteDatabase
     * query(String table, String[] columns, String selection,
     * String[] selectionArgs, String groupBy, String having,
     * String orderBy)
     */
    public Cursor query(String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    public int delete(String selection, String[] selectionArgs) {
        return sqLiteDatabase.delete(TABLE_NAME, selection, selectionArgs);
    }
}
