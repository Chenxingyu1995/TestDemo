package com.example.duangniu000.test2.data.response;


import com.example.duangniu000.test2.data.ImageType;
import com.example.duangniu000.test2.data.response.ShowApiPagerResponse;
import com.example.duangniu000.test2.data.response.ShowApiResponse;

public class ImageTypeResponse extends ShowApiResponse {

    private ShowApiPagerResponse<ImageType> showapi_res_body;

    public ShowApiPagerResponse<ImageType> getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowApiPagerResponse<ImageType> showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
