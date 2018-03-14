package com.example.duangniu000.test2.Okhttp;


import java.util.HashMap;

public class Params extends HashMap<String, Object> {


    private Params() {
    }

    public static Params build() {
        Params params = new Params();
        params.add("showapi_appid", "57487");
        params.add("showapi_sign", "f4cec95e4bb34f249627d873bdd28537");
        return params;
    }

    public void add(String key, Object value) {
        super.put(key, value);
    }


}
