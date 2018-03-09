package com.example.duangniu000.test2.Activity;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.duangniu000.test2.CoustomView.GoldView;
import com.example.duangniu000.test2.CoustomView.RandomTextView;
import com.example.duangniu000.test2.CoustomView.StatusBarView;
import com.example.duangniu000.test2.Fragment.GuessingFragment;
import com.example.duangniu000.test2.Fragment.JokerListFragment;
import com.example.duangniu000.test2.Fragment.StringListFragment;
import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Util.StatusBarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @BindView(R.id.status_bar)
    StatusBarView statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        boolean translucent = StatusBarHelper.translucent(this);
        StatusBarHelper.setStatusBarLightMode(this);
        statusBar.setStatusBar(translucent);
        statusBar.setBackgroundColor(getColorRes(R.color.colorPrimary));
    }

    /**
     * Drawable 颜色转化类
     *
     * @param drawable
     * @param color    资源
     * @return 改变颜色后的Drawable
     */
    public static Drawable tintDrawable(@NonNull Drawable drawable, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));
        return wrappedDrawable;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.start, R.id.network, R.id.buju})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.start:
                FragmentActivity.launcher(this, JokerListFragment.class);
                break;
            case R.id.network:
                FragmentActivity.launcher(this, GuessingFragment.class);
                break;
            case R.id.buju:
                FragmentActivity.launcher(this, StringListFragment.class);
                break;
        }
    }


    public class ItemDecoration extends RecyclerView.ItemDecoration {


        private Paint dividerPaint;

        private Paint drawText;

        public ItemDecoration() {
            super();
            dividerPaint = new Paint();
            dividerPaint.setColor(getColorRes(R.color.colorPrimary));
            dividerPaint.setAntiAlias(true);
            drawText = new Paint();
            drawText.setTextSize(40);
            drawText.setColor(Color.parseColor("#ffffff"));
            drawText.setAntiAlias(true);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
            int childCount = manager.getChildCount();
            int position = manager.getPosition(view);
            if (position % 5 == 0) {
                view.setTag(String.valueOf("di" + position / 5 + "zu"));
                outRect.set(0, 60, 0, 0);
            } else {
                view.setTag(null);
                outRect.set(0, 0, 0, 0);
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

            int childCount = parent.getChildCount();
            int left = 0;
            int right = parent.getWidth();

            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);

                if (view.getTag() != null) {
                    float bottom = view.getTop();
                    float top = view.getTop() - 60;
                    c.drawRect(left, top, right, bottom, dividerPaint);
                    String text = (String) view.getTag();


                    Rect rect = new Rect();
                    drawText.getTextBounds(text, 0, text.length() - 1, rect);
                    int strwid = rect.width();
                    int strhei = rect.height();
                    int mb = (60 - strhei) / 2;

                    c.drawText(text, view.getLeft() + 10, view.getTop() - mb, drawText);
                }
            }
        }
    }
}
