package com.example.duangniu000.test2.CoustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

public class RandomTextView extends android.support.v7.widget.AppCompatTextView {
    //高位快
    public static final int FIRSTF_FIRST = 0;
    //高位慢
    public static final int FIRSTF_LAST = 1;
    //速度相同
    public static final int ALL = 2;
    //用户自定义速度
    public static final int USER = 3;
    //偏移速度类型
    private int pianyiliangTpye = FIRSTF_LAST;

    //   滚动总行数 可设置
    private int maxLine = 10;
    //   当前字符串长度
    private int numLength = 0;
    //   当前text
    private String string;

    //滚动速度数组
    private int[] pianyilianglist;
    //总滚动距离数组
    private int[] pianyiliangSum;
    //滚动完成判断
    private int[] overLine;

    private Paint p;
    //第一次绘制
    private boolean firstIn = true;
    //滚动中
    private boolean auto = false;

    //string int值列表
    private ArrayList<String> arrayListText;

    //字体宽度
    private float f0;

    //基准线
    private int baseline;

    private int measuredHeight;

    private float[] widths;

    public RandomTextView(Context context) {
        this(context, null);

    }

    public RandomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public RandomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    private void init() {
        Typeface typeFace = Typeface.createFromAsset(getResources().getAssets(), "fonts/Arvo-Regular.ttf");
        setTypeface(typeFace);

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                p = getPaint();
                widths = new float[14];
                p.getTextWidths("0123456789KMB.", widths);
                Paint.FontMetricsInt fontMetrics = p.getFontMetricsInt();
                measuredHeight = getMeasuredHeight();
                baseline = (measuredHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
                f0 = widths[0];
            }
        });


    }

    //按系统提供的类型滚动
    public void setPianyilian(int pianyiliangTpye) {
        this.string = getText().toString();

        pianyiliangSum = new int[string.length()];
        overLine = new int[string.length()];
        pianyilianglist = new int[string.length()];
        switch (pianyiliangTpye) {
            case FIRSTF_FIRST:
                for (int i = 0; i < string.length(); i++) {
                    pianyilianglist[i] = 20 - i;
                }

                break;
            case FIRSTF_LAST:
                for (int i = 0; i < string.length(); i++) {
                    pianyilianglist[i] = 5 + i;
                }

                break;
            case ALL:
                for (int i = 0; i < string.length(); i++) {
                    pianyilianglist[i] = 15;
                }

                break;
        }
    }

    //自定义滚动速度数组
    public void setPianyilian(int[] list) {
        this.string = getText().toString();

        pianyiliangSum = new int[list.length];
        overLine = new int[list.length];
        pianyilianglist = list;


    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (firstIn) {
            super.onDraw(canvas);
            Log.e("onDraw", "--");
        } else {
            drawNumber(canvas);
        }
    }

    //绘制
    private void drawNumber(Canvas canvas) {
        int point_offset = 0;

        for (int j = 0; j < numLength; j++) {
            String s = arrayListText.get(j);
            switchNumWidth(s);
            boolean b = TextUtils.isDigitsOnly(s);
            for (int i = 1; i < maxLine; i++) {
                if (i == maxLine - 1 && i * baseline + pianyiliangSum[j] <= baseline) {
                    pianyilianglist[j] = 0;
                    overLine[j] = 1;
                    int auto = 0;
                    for (int k = 0; k < numLength; k++) {
                        auto += overLine[k];
                    }
                    if (auto == numLength * 2 - 1) {
                        this.auto = false;
                        handler.removeCallbacks(task);
                        invalidate();
                    }


                }
                if (overLine[j] == 0) {
                    if (b) {
                        drawText(canvas, setBack(Integer.parseInt(s), maxLine - i - 1) + "", point_offset, i * baseline + pianyiliangSum[j], p);
                    } else {
                        drawText(canvas, s + "", point_offset, i * baseline + pianyiliangSum[j], p);
                    }

                } else {
                    //定位后画一次就好啦
                    if (overLine[j] == 1) {
                        overLine[j]++;
                        drawText(canvas, arrayListText.get(j) + "", point_offset, baseline, p);
                    }
                }
            }
            point_offset += f0;

        }

    }


    private void animEnd() {

        postDelayed(new Runnable() {
            @Override
            public void run() {

                CharSequence sequence = getText();
                if (!TextUtils.isEmpty(sequence)) {
                    setText(sequence);
                }
                if (listener != null) {
                    listener.onAnimEnd();
                }
            }
        }, 0);
    }


    private void switchNumWidth(String str) {
        switch (str) {
            case "0":
                f0 = widths[0];
                break;
            case "1":
                f0 = widths[1];
                break;
            case "2":
                f0 = widths[2];
                break;
            case "3":
                f0 = widths[3];
                break;
            case "4":
                f0 = widths[4];
                break;
            case "5":
                f0 = widths[5];
                break;
            case "6":
                f0 = widths[6];
                break;
            case "7":
                f0 = widths[7];
                break;
            case "8":
                f0 = widths[8];
                break;
            case "9":
                f0 = widths[9];
                break;
            case "K":
                f0 = widths[10];
                break;
            case "M":
                f0 = widths[11];
                break;
            case "B":
                f0 = widths[12];
                break;
            case ".":
                f0 = widths[13];
                break;

        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        firstIn = true;
        super.setText(text, type);
    }

    //设置上方数字0-9递减
    private int setBack(int c, int back) {

        if (back == 0) return c;

        back = back % 10;

        int re = c - back;

        if (re < 0) re = re + 10;

        return re;
    }

    //开始滚动
    public void start() {
        firstIn = false;
        this.string = getText().toString();
        numLength = string.length();
        arrayListText = getList(string);
        handler.postDelayed(task, 17);
        auto = true;

        postDelayed(new Runnable() {
            @Override
            public void run() {
                animEnd();
            }
        }, 800);

    }

    public void setMaxLine(int l) {
        this.maxLine = l;
    }

    private ArrayList<String> getList(String s) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (int i = 0; i < s.length(); i++) {

            String ss = s.substring(i, i + 1);

            // int a = Integer.parseInt(ss);

            arrayList.add(ss);
        }
        return arrayList;

    }

    private static final Handler handler = new Handler();

    public void destroy() {
        auto = false;
        handler.removeCallbacksAndMessages(null);
    }

    private final Runnable task = new Runnable() {

        public void run() {
            if (auto) {
                handler.postDelayed(this, 20);
                for (int j = 0; j < numLength; j++) {
                    pianyiliangSum[j] -= pianyilianglist[j];
                }
                invalidate();
            }
        }
    };


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destroy();
    }

    private void drawText(Canvas mCanvas, String text, float x, float y, Paint p) {
        if (mCanvas == null || text == null) return;
        if (p == null) {
            p = getPaint();
        }
        if (y >= -measuredHeight && y <= 2 * measuredHeight)
            mCanvas.drawText(text + "", x, y, p);
    }

    private OnAnimEndListener listener;

    public void setListener(OnAnimEndListener listener) {
        this.listener = listener;
    }

    public interface OnAnimEndListener {
        void onAnimEnd();
    }

}
