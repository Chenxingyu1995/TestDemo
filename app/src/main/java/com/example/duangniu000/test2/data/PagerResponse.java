package com.example.duangniu000.test2.data;


import java.util.List;

public class PagerResponse<T> {
    private int allNum;
    private int allPages;
    private List<T> contentlist;
    private int currentPage;
    private int maxResult;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public List<T> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<T> contentlist) {
        this.contentlist = contentlist;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    @Override
    public String toString() {
        return "PagerResponse{" +
                "allNum=" + allNum +
                ", allPages=" + allPages +
                ", contentlist=" + contentlist +
                ", currentPage=" + currentPage +
                ", maxResult=" + maxResult +
                '}';
    }
}
