package com.example.duangniu000.test2.myEvent;


public class Notice {


    public static final int DOWN = 1;
    public static final int OK = 2;
    public static final int START = 3;
    public static final int FAIL = 3;


    private Object object;

    private int type;

    private float args1;
    private float args2;

    public void setArgs1(float args1) {
        this.args1 = args1;
    }

    public void setArgs2(float args2) {
        this.args2 = args2;
    }

    public Notice() {

    }

    public Notice(Object object, int type) {
        this.object = object;
        this.type = type;
    }


    public void setType(int type) {
        this.type = type;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public int getType() {
        return type;
    }


}
