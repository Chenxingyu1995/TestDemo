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

  /*
    final Dispatcher dispatcher;  //分发器
    final Proxy proxy;  //代理
    final List<Protocol> protocols; //协议
    final List<ConnectionSpec> connectionSpecs; //传输层版本和连接协议
    final List<Interceptor> interceptors; //拦截器
    final List<Interceptor> networkInterceptors; //网络拦截器
    final ProxySelector proxySelector; //代理选择
    final CookieJar cookieJar; //cookie
    final Cache cache; //缓存
    final InternalCache internalCache;  //内部缓存
    final SocketFactory socketFactory;  //socket 工厂
    final SSLSocketFactory sslSocketFactory; //安全套接层socket 工厂，用于HTTPS
    final CertificateChainCleaner certificateChainCleaner; // 验证确认响应证书 适用 HTTPS 请求连接的主机名。
    final HostnameVerifier hostnameVerifier;    //  主机名字确认
    final CertificatePinner certificatePinner;  //  证书链
    final Authenticator proxyAuthenticator;     //代理身份验证
    final Authenticator authenticator;      // 本地身份验证
    final ConnectionPool connectionPool;    //连接池,复用连接
    final Dns dns;  //域名
    final boolean followSslRedirects;  //安全套接层重定向
    final boolean followRedirects;  //本地重定向
    final boolean retryOnConnectionFailure; //重试连接失败
    final int connectTimeout;    //连接超时
    final int readTimeout; //read 超时
    final int writeTimeout; //write 超时
    */


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

    OkHttpClient client = new OkHttpClient();
    private void test() {

        //一种：参数请求体
        FormBody paramsBody = new FormBody.Builder()
                .add("id", "currentPlan.getPlanId() + ")
                .add("name", "currentPlan.getName()")
                .add("volume", "currentPlan.getVolume()")
                .add("type", "currentPlan.getType() + ")
                .add("mode", "currentPlan.getMode() +")
                .build();


        //二种：文件请求体
        MediaType type = MediaType.parse("application/octet-stream");//"text/xml;charset=utf-8"
        File file = new File("/data/data/com.example.company/files/plan/plans.xml");
        RequestBody fileBody = RequestBody.create(type, file);


        //三种：混合参数和文件请求
        RequestBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.MIXED)
                //一样的效果
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"params\""), paramsBody)
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"plans.xml\""), fileBody)

                //一样的效果
        /*
         *
        .addFormDataPart("id",currentPlan.getPlanId()+"")
        .addFormDataPart("name",currentPlan.getName())
        .addFormDataPart("volume",currentPlan.getVolume())
        .addFormDataPart("type",currentPlan.getType()+"")
        .addFormDataPart("mode",currentPlan.getMode()+"")
        .addFormDataPart("params","plans.xml",fileBody)

        */
                .build();


        Request request = new Request.Builder().url("http://192.168.1.121:8080/Server/Service")
                .addHeader("User-Agent", "android")
                .header("Content-Type", "text/html; charset=utf-8;")
                .post(multipartBody)//传参数、文件或者混合，改一下就行请求体就行
                .build();



    }
}
