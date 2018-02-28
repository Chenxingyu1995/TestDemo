package com.example.duangniu000.test2.Fragment;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import com.example.duangniu000.test2.Util.DisplayHelper;
import java.util.Collection;

public class BaseFragment extends Fragment {


    private int getScreenHeight() {
        return DisplayHelper.getScreenHeight(getContext());
    }

    private int getScreenWidth() {
        return DisplayHelper.getScreenWidth(getContext());
    }


    /**
     * 单位转换: dp -> px
     */
    protected int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    /**
     * 单位转换:sp -> px
     */
    protected int px2dp(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }


    protected boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    protected boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }


}
