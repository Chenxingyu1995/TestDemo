package com.example.duangniu000.test2.Request;


import java.util.HashMap;

public class Params extends HashMap<String, Object> {


    private Params() {
    }

    public static Params build() {
        return new Params();
    }

    public void add(String key, Object value) {
        super.put(key, value);
    }


}
