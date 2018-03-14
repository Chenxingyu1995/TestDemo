package com.example.duangniu000.test2.data;


import java.util.List;

public class ShowApiImageTypeList extends Response {
    private String ret_code;
    private String ret_message;
    private List<ImageType> data;

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_message() {
        return ret_message;
    }

    public void setRet_message(String ret_message) {
        this.ret_message = ret_message;
    }

    public void setData(List<ImageType> data) {
        this.data = data;
    }

    public List<ImageType> getData() {
        return data;
    }
}
