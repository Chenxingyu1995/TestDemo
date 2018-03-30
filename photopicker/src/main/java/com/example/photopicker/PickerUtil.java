package com.example.photopicker;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PickerUtil {

    public static File getCameraFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "PhotoPicker");
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    }


    static void startUCrop(Uri sourceUri, Uri destinationUri, Activity context) {
        UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(16, 9)
                .withMaxResultSize(1080, 1920)
                .start(context);
    }

    public static Uri getCropUri(Activity activity, File file) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            String authority = activity.getPackageName() + ".FileProvider";
            Log.e("get24MediaFileUri", "authority:" + authority);
            return FileProvider.getUriForFile(activity, authority, file);
        } else {
            return Uri.fromFile(file);
        }


    }


    public static File getCropFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/PhotoPicker", "/UCrop");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        return new File(mediaStorageDir, "Crop" + timeStamp + ".jpg");
    }


    /**
     * 相机拍照的Uri
     **/
    public static Uri getCameraUri(Activity activity, File file) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return get24MediaFileUri(activity, file);
        } else {
            return getMediaFileUri(activity, file);
        }
    }

    private static Uri getMediaFileUri(Activity activity, File file) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "PhotoPicker");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return Uri.fromFile(file);
    }


    private static Uri get24MediaFileUri(Activity activity, File file) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "PhotoPicker");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // authority 必须和 FileProvider 一样
        String authority = activity.getPackageName() + ".FileProvider";
        Log.e("get24MediaFileUri", "authority:" + authority);
        return FileProvider.getUriForFile(activity, authority, file);
    }


}
