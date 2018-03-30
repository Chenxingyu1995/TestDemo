package com.example.duangniu000.test2.data.response;


import com.example.duangniu000.test2.data.ImageList;


public class ImageListInTypeResponse extends ShowApiResponse {

    private ShowApiPagerResponse<ImageList> showapi_res_body;

    public ShowApiPagerResponse<ImageList> getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowApiPagerResponse<ImageList> showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
