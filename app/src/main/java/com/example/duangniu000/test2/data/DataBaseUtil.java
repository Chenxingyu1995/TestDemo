package com.example.duangniu000.test2.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;
import android.util.Log;


public class DataBaseUtil {

    private static DataBaseUtil util;
    private SQLiteDatabase database;
    private static final String TALE_NAME = "password";

    public static final String PASSWORD = "password";

    private DataBaseUtil(SQLiteDatabase database) {
        this.database = database;
    }

    public static void build(SQLiteDatabase database) {
        if (util == null) {
            util = new DataBaseUtil(database);
        }
    }

    public static SQLiteDatabase getDatabase() {
        return util.database;
    }


    private static void execSQL(String sql) {
        util.database.execSQL(sql);
    }

    public static String findPassWord() {
        String sql = "select * from " + TALE_NAME + " where key = ?";
        Cursor cursor = rawQuery(sql, new String[]{PASSWORD});
        if (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("value"));
        }
        return "";
    }

    public static void insert(ContentValues values) {
        getDatabase().insert(TALE_NAME, null, values);
    }

    public static void update(String key, ContentValues values) {
        getDatabase().update(TALE_NAME, values, key = "?", new String[]{key});
    }


    private static Cursor rawQuery(String sql, String[] selectionArgs, CancellationSignal cancellationSignal) {
        return util.database.rawQuery(sql, selectionArgs, cancellationSignal);
    }

    private static Cursor rawQuery(String sql, String[] selectionArgs) {
        return util.database.rawQuery(sql, selectionArgs);
    }

    private static Cursor rawQuery(String sql) {
        return util.database.rawQuery(sql, null);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}

