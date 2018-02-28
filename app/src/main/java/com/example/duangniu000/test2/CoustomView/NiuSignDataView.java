package com.example.duangniu000.test2.CoustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created on 2017/9/14.
 *
 * @author ChengXingyu
 */

public class NiuSignDataView extends View {
    /**
     * 圆弧绘制的角度
     **/
    private float sweepAngle = 360;
    /**
     * 绘制圆环的画笔
     **/
    private Paint paint;
    /**
     * 绘制文字的画笔
     **/
    private Paint textPaint;
    /**
     * 圆环的宽度
     **/
    private int paint_width;
    /**
     * 圆环的区域
     **/
    private RectF rectF;
    /**
     * View的宽度
     **/
    int width;
    /**
     * View的高度
     **/
    int height;
    /**
     * 倒计时的时间文字
     **/
    private String time = "00:00:00";
    /**
     * 倒计时的时间
     **/
    private int datal = 0;
    /**
     * 倒计时的最大时长
     **/
    private int Max_time = 86400;

    private OnCompLeteListener onCompLeteListener;

    public interface OnCompLeteListener {
        void onComplete();
    }

    public void setOnCompLeteListener(OnCompLeteListener onCompLeteListener) {
        this.onCompLeteListener = onCompLeteListener;
    }

    private CountDownTimer timer;

    public NiuSignDataView(Context context) {
        super(context);
        initPaint();
    }

    public NiuSignDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public NiuSignDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private static final String TAG = "MySignDataView";

    @SuppressLint("NewApi")
    public NiuSignDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initPaint() {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(dip2px(12));
        textPaint.setColor(0xFF4ed5c7);
        paint_width = dip2px(4);
        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(paint_width);
        float sweepAngle = ((float) datal) / 86400f * 360f;
        this.sweepAngle = (360 - sweepAngle);
    }

    private String formatTime() {
        int hours = datal / 3600;
        int minute = (datal / 60) - (hours * 60);
        int second = datal - (hours * 60 * 60) - (minute * 60);
        String s_hours;
        String s_minute;
        String s_second;
        if (hours < 10) {
            s_hours = "0" + String.valueOf(hours);
        } else {
            s_hours = String.valueOf(hours);
        }

        if (minute < 10) {
            s_minute = "0" + String.valueOf(minute);
        } else {
            s_minute = String.valueOf(minute);
        }

        if (second < 10) {
            s_second = "0" + String.valueOf(second);
        } else {
            s_second = String.valueOf(second);
        }

        return s_hours + ":" + s_minute + ":" + s_second;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        time = formatTime();
        float v = rectF.bottom - rectF.top;
        float ascent = textPaint.ascent();
        float descent = textPaint.descent();
        float height_offset = (descent - ascent) / 2;
        float weight_offset = textPaint.measureText(time) / 2;
        paint.setColor(0xFFf7f7f7);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, width / 2 - paint_width + 4, paint);
        canvas.drawText(time, 0, time.length(), width / 2 - weight_offset, v / 2 + height_offset, textPaint);
        paint.setColor(0xFFe2e1e1);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF, 0, 360, false, paint);
        Shader shaed = new LinearGradient(width / 2, 0, width, height, 0xFF9face6, 0xFF74ebd5, Shader.TileMode.CLAMP);
        paint.setShader(shaed);
        canvas.drawArc(rectF, 270, sweepAngle, false, paint);
        paint.setShader(null);

    }

    public void setStartData(int datal) {
        this.datal = datal;
        initTimer();
    }

    private void initTimer() {
        if (timer != null) timer.cancel();
        timer = new CountDownTimer(datal * 1000, 1000) {
            @Override
            public void onTick(long l) {
                setDatal(--datal);
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    private void setDatal(int datal) {
        this.datal = datal;
        float sweepAngle = ((float) datal) / 86400f * 360f;
        this.sweepAngle = 360 - sweepAngle;

        if (datal == 0) {
            this.sweepAngle = 0;
            if (onCompLeteListener != null) {
                onCompLeteListener.onComplete();
            }
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        rectF = new RectF(paint_width / 2, paint_width / 2, width - paint_width, height - paint_width);
    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (timer != null)
            timer.cancel();
    }
}
