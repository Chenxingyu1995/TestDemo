package com.example.duangniu000.test2.data;


public class Joker {
    /**
     * 时间
     **/
    private String ct;
    // 内容
    private String text;
    // 标题
    private String title;

    private String img;
    // 0 文字  2 图 3 动图
    private int type;

    private int id;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Joker{" +
                "ct='" + ct + '\'' +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", type=" + type +
                ", id=" + id +
                '}';
    }
}
