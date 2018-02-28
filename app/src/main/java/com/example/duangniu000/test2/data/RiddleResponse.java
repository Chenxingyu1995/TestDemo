package com.example.duangniu000.test2.data;


import android.support.v4.view.PagerAdapter;

public class RiddleResponse extends Resopnse {

    private PagerResponse<Riddle> owapi_res_body;

    public void setOwapi_res_body(PagerResponse<Riddle> owapi_res_body) {
        this.owapi_res_body = owapi_res_body;
    }
}
