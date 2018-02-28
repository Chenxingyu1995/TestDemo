package com.example.duangniu000.test2.Request;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.duangniu000.test2.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadClient {

    private LoadListener loadListener;

    public void setLoadListener(LoadListener loadListener) {
        this.loadListener = loadListener;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (loadListener == null) return;
            switch (msg.what) {
                case 200:
                    //准备
                    loadListener.loadStart();
                    break;
                case 201:
                    //开始
                    loadListener.loading(msg.arg1);
                    break;
                case 202:
                    //结束
                    loadListener.loadEnd();
                    break;
                case 204:
                    //失败
                    loadListener.loadFail();
                    break;

            }
        }
    };

    private DownloadClient() {
    }


    public static DownloadClient build() {
        return new DownloadClient();
    }

    public void downloadFile(String url) {
        Message msg = handler.obtainMessage();
        msg.what = 200;
        handler.sendMessage(msg);
        @SuppressLint("SimpleDateFormat")
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        final File saveFile = new File(getSaveFile(), time + ".gif");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Message message = handler.obtainMessage();
                message.what = 204;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    InputStream inputStream = null;
                    FileOutputStream fos = null;
                    try {
                        inputStream = response.body().byteStream();
                        fos = new FileOutputStream(saveFile);
                        byte[] buff = new byte[1024 * 1024];
                        long total = response.body().contentLength();
                        int len = 0;
                        long sumlen = 0;
                        while ((len = inputStream.read(buff)) != -1) {
                            sumlen += len;
                            fos.write(buff, 0, len);
                            float s1 = total;
                            float s2 = sumlen;
                            Message msg = handler.obtainMessage();
                            msg.arg1 = (int) ((s2 / s1) * 100);
                            msg.what = 201;
                            handler.sendMessage(msg);
                        }
                        fos.flush();

                    } catch (Exception ignored) {

                    } finally {
                        try {
                            if (inputStream != null)
                                inputStream.close();
                        } catch (IOException e) {
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                        }
                        updateD(saveFile, App.getContext());
                        Message msg = handler.obtainMessage();
                        msg.what = 202;
                        handler.sendMessage(msg);
                    }
                }
            }
        });

    }

    private void updateD(File file, Context context) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    private static String getSaveFile() {
        File directory = Environment.getExternalStorageDirectory();
        String path = directory.getAbsolutePath();
        File file = new File(path + "/downGif");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();

    }

    public interface LoadListener {

        void loadStart();

        void loading(int pos);

        void loadEnd();

        void loadFail();
    }


}
