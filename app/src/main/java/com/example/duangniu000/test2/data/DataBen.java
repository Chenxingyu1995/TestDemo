package com.example.duangniu000.test2.data;


public class DataBen {

    private int ret_code;

    private PagerResponse<Guessing> pb;

    public void setPb(PagerResponse pb) {
        this.pb = pb;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public int getRet_code() {
        return ret_code;
    }

    public PagerResponse getPb() {
        return pb;
    }
}
