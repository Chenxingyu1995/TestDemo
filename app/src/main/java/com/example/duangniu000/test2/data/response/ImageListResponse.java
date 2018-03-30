package com.example.duangniu000.test2.data.response;


public class ImageListResponse extends ShowApiResponse {

    private ShowApiPagerResponse<String> showapi_res_body;

    public ShowApiPagerResponse<String> getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowApiPagerResponse<String> showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
