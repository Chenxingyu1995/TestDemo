package com.example.duangniu000.test2.Util;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;


import java.io.InputStream;
import java.lang.reflect.Field;

public class Util {


    public static boolean CheckPermission(String permission, Context context) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static void requestPermission(String[] permission, Activity activity, int code) {
        ActivityCompat.requestPermissions(activity, permission, code);
    }

    public static int randomColor() {
        int c = Color.argb(255, (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        return c;
    }

    public static void varArgus(String... value) {
        for (String s : value) {
            Log.e("test", "test: " + s);
        }
    }

    /**
     * @param array_resource 数组Id
     * @param index          数组角标
     * @param context        上下文
     * @see Util#getIntegerResources(int, int, Context)
     */
    public static int getIntegerResources(int index, int array_resource, Context context) {
        TypedArray array = context.getResources().obtainTypedArray(array_resource);
        int resourceId = array.getResourceId(index, 0);
        return resourceId;
    }

    /**
     * 读取bitmap
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inSampleSize = 2;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 缩放
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 合并
     */
    public static Bitmap mergrBitmap(Bitmap bitmap1, Bitmap bitmap2) {
        Bitmap bitmap3 = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight() + bitmap2.getHeight(), bitmap1.getConfig());
        Canvas canvas = new Canvas(bitmap3);
        canvas.drawBitmap(bitmap1, new Matrix(), null);
        canvas.drawBitmap(bitmap2, 0, bitmap1.getHeight(), null);

        return bitmap3;
    }

    /**
     * 获取view的图
     */
    public static Bitmap getViewBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    /**
     * 复制
     */
    private void createBitmap(Bitmap bitmap) {
        Bitmap bigBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas bigCanvas = new Canvas(bigBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        bigCanvas.drawPaint(paint);

        Matrix matrix = new Matrix();
        matrix.setScale(0.8f, 0.8f);
        //原图绘制
        bigCanvas.drawBitmap(bigBitmap, 0, 0, paint);
    }

    /**
     * 状态栏高度
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = com.example.duangniu000.test2.App.getContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();

        }
        return sbar;
    }

    public Bitmap shotRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int item_hright;
            Paint paint = new Paint();
            Canvas bigCanvas = null;
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                Bitmap drawingCache = getViewBitmap(holder.itemView);
                item_hright = holder.itemView.getMeasuredHeight();
                if (bigBitmap == null) {
                    bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), item_hright * (size - 1), Bitmap.Config.RGB_565);
                }

                if (bigCanvas == null) {
                    bigCanvas = new Canvas(bigBitmap);
                }
                paint.setColor(Color.WHITE);
                bigCanvas.drawPaint(paint);
                bigCanvas.drawBitmap(drawingCache, 0, i * item_hright, paint);
                recycleBitmap(drawingCache);
            }
        }
        return bigBitmap;
    }

    private void recycleBitmap(Bitmap drawingCache) {
        if (!drawingCache.isRecycled()) {
            drawingCache.recycle();
            drawingCache = null;
        }
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dipValue, context.getResources().getDisplayMetrics());
    }

    public static int dp2px(Context context, int value) {
        return ((int) (value * context.getResources().getDisplayMetrics().density));
    }


}
