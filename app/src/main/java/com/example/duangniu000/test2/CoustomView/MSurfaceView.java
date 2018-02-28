package com.example.duangniu000.test2.CoustomView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Util.Util;

public class MSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    //绘图的画布
    private Canvas mCanvas;
    private SurfaceHolder mHolder;
    private Paint paint;

    private int start_left;
    private int starrt_top;

    public MSurfaceView(Context context) {
        this(context, null);
    }

    public MSurfaceView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public MSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mHolder = getHolder();//获取SurfaceHolder对象
        mHolder.addCallback(this);//注册SurfaceHolder的回调方法

        setFocusable(true);
        setClickable(true);
        setFocusableInTouchMode(true);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);

        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }


    // 创建
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        new Thread(this).start();
    }

    // 改变
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    // 销毁
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                starrt_top = (int) event.getY();
                start_left = (int) event.getX();
                run();
                break;
        }
        return super.onTouchEvent(event);

    }

    @Override
    public void run() {

        try {
            mCanvas = mHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);
            paint.setColor(Util.randomColor());
            RectF rectF = new RectF();
            rectF.set(start_left, starrt_top, start_left + 200, starrt_top + 200);
            mCanvas.drawRoundRect(rectF, 10, 10, paint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);//保证每次都将绘图的内容提交
            }
        }
    }
}
