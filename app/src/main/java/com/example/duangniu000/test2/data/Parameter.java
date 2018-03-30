package com.example.duangniu000.test2.data;


import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;

public class Parameter extends HashMap<String, String> {

    public static final String STRING = "TEXT";
    public static final String INTEGER = "INTEGER";


    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public String put(String key, String value) {
        return super.put(key, value);
    }

    public String toSQL(String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("create table ").append(tableName).append("(");

        Iterator<String> iterator = keySet().iterator();
        for (; iterator.hasNext(); ) {
            String key = iterator.next();
            builder.append(key).append(" ").append(get(key));

            if (iterator.hasNext()) {
                builder.append(",");
            } else {
                builder.append(")");
            }
        }
        Log.i("toSQL", builder.toString());
        return builder.toString();
    }
}