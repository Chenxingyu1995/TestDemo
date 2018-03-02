package com.example.duangniu000.test2.Request;


import java.io.File;
import java.io.IOException;
import java.net.Proxy;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestClent {




    /**
     * 属性：
     text/html ： HTML格式
     text/plain ：纯文本格式
     text/xml ：  XML格式
     image/gif ：gif图片格式
     image/jpeg ：jpg图片格式
     image/png：png图片格式
     以application开头的媒体格式类型：

     application/xhtml+xml ：XHTML格式
     application/xml     ： XML数据格式
     application/atom+xml  ：Atom XML聚合格式
     application/json    ： JSON数据格式
     application/pdf       ：pdf格式
     application/msword  ： Word文档格式
     application/octet-stream ： 二进制流数据（如常见的文件下载）
     application/x-www-form-urlencoded ： <form encType=””>中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）

     另外一种常见的媒体格式是上传文件之时使用的：
     multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式
     注意：MediaType.parse("image/png")里的"image/png"不知道该填什么，可以参考---》http://www.w3school.com.cn/media/media_mimeref.asp
     如何使用呢？(在请求体里面写入类型和需要写入的数据，通过post请求)
     String body = "hdsoifhjoihdsfh";
     RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, body);
     */

    /**
     * http://www.w3school.com.cn/media/media_mimeref.asp
     **/


    private void test() {


    }
}
