package com.example.duangniu000.test2.Activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.duangniu000.test2.Adaper.ViewPagerAdapter;
import com.example.duangniu000.test2.CoustomView.StatusBarView;
import com.example.duangniu000.test2.Fragment.ImageListInTypeFragment;
import com.example.duangniu000.test2.Okhttp.GsonCallBack;
import com.example.duangniu000.test2.Okhttp.Params;
import com.example.duangniu000.test2.Okhttp.RequestClient;
import com.example.duangniu000.test2.Okhttp.Url;
import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Util.StatusBarHelper;
import com.example.duangniu000.test2.data.ImageType;
import com.example.duangniu000.test2.data.response.ImageTypeResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


public class MainActivity extends BaseActivity {

    @BindView(R.id.status_bar)
    StatusBarView statusBar;
    @BindView(R.id.tabView)
    TabLayout tabView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<ImageType> title;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        boolean translucent = StatusBarHelper.translucent(this);
        StatusBarHelper.setStatusBarLightMode(this);
        statusBar.setStatusBar(translucent);
        statusBar.setBackgroundColor(getColorRes(R.color.colorPrimary));
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        netWork();
    }


    private void netWork() {
        Params build = Params.build();
        RequestClient.Build().parms(build).url(Url.m1n2 + Url.leixin).from().newCall(new GsonCallBack<ImageTypeResponse>() {
            @Override
            public void Response(Call call, ImageTypeResponse response) {
                title = response.getShowapi_res_body().getData();
                int size = title.size();
                List<Fragment> fragments = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    ImageListInTypeFragment listFragment = new ImageListInTypeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", title.get(i).getId());
                    listFragment.setArguments(bundle);
                    fragments.add(listFragment);
                }
                adapter.setList(fragments);
                adapter.setTitle(title);
                viewPager.setAdapter(adapter);
                tabView.setupWithViewPager(viewPager, true);
            }

            @Override
            public void Failure(Call call, IOException e) {

            }
        });
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

    public static boolean isActive = true; //全局变量

    @Override
    protected void onResume() {
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
            Log.i("ACTIVITY", "程序从后台唤醒");
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            //app 进入后台
            //记录当前已经进入后台
            isActive = false;
            Log.i("ACTIVITY", "程序进入后台");
        }
        super.onStop();
    }

    /**
     * APP是否处于前台唤醒状态
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = null;
        if (activityManager != null) {
            appProcesses = activityManager.getRunningAppProcesses();
        }
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }


}
