package com.example.duangniu000.test2.data;


public class JokerResponse extends Resopnse {

    private PagerResponse<Joker> showapi_res_body;

    public PagerResponse getShowapi_res_body() {
        return showapi_res_body;
    }
    public void setShowapi_res_body(PagerResponse showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    @Override
    public String toString() {
        return "JokerResponse{" +
                "showapi_res_body=" + showapi_res_body +
                '}';
    }
}
