package com.example.duangniu000.test2.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String name = "db";
    private static int version = 1;

    public SQLiteHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int firstVersion = 0;
        Log.e("SQLiteDatabase", "onCreate,firstVersion:" + firstVersion + ",version" + version);
        onUpgrade(db, firstVersion, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 0:
                    createTable(db);
                    break;
            }
        }
    }

    private void createTable(SQLiteDatabase database) {
        Parameter parameter = new Parameter();
        parameter.put("key", Parameter.STRING);
        parameter.put("value", Parameter.STRING);
        database.execSQL(parameter.toSQL("password"));

        ContentValues values = new ContentValues();
        values.put("key", "password");
        values.put("value", "988655");
        database.insert("password", null, values);
    }
}
