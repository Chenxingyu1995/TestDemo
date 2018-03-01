package com.example.duangniu000.test2.data;


import java.util.List;

public class Guessing {
    /**
     * 搞笑=gxmy,
     * 字谜=zmmy,
     * 成语=cymy,
     * 动物=dwmy,
     **/
    public static final String type1 = "gxmy";
    public static final String type2 = "zmmy";
    public static final String type3 = "cymy";
    public static final String type4 = "dwmy";


    private String answer;
    private String title;
    private String typeId;
    private String typeName;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
