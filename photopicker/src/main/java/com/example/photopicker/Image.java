package com.example.photopicker;


public class Image {
    private String path;
    private boolean select;


    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public boolean isSelect() {
        return select;
    }
}
