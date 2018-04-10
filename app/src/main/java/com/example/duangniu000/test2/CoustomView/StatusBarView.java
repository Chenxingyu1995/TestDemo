package com.example.duangniu000.test2.CoustomView;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Util.StatusBarHelper;
public class StatusBarView extends ViewGroup {

    TextView titleTv;
    private int left_type;
    private int right_type;
    private String title;
    private View status_bar_space;
    private RelativeLayout left;
    private TextView left_text;
    private ImageView left_icon;
    private RelativeLayout right;
    private TextView right_text;
    private ImageView right_icon;
    public static final int ICON = 0;
    public static final int TEXT = 1;
    private @DrawableRes
    int left_icon_res;
    private @DrawableRes
    int right_icon_res;
    private String leftStr;
    private String rightStr;

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getTypeArray(context, attrs);
        initChildView();
        initType();

    }

    private void getTypeArray(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatusBarView);
        left_type = typedArray.getInteger(R.styleable.StatusBarView_left_type, 0);
        right_type = typedArray.getInteger(R.styleable.StatusBarView_right_type, 0);
        title = typedArray.getString(R.styleable.StatusBarView_title_str);
        right_icon_res = typedArray.getResourceId(R.styleable.StatusBarView_right_icon, 0);
        left_icon_res = typedArray.getResourceId(R.styleable.StatusBarView_left_icon, 0);
        leftStr = typedArray.getString(R.styleable.StatusBarView_left_str);
        rightStr = typedArray.getString(R.styleable.StatusBarView_right_str);
        typedArray.recycle();
    }

    private void initChildView() {
        View view = inflate(getContext(), R.layout.title_bar, this);
        status_bar_space = view.findViewById(R.id.status_space);
        titleTv = findViewById(R.id.titleTv);
        left = this.findViewById(R.id.left_root);
        left_text = this.findViewById(R.id.left_text);
        left_icon = this.findViewById(R.id.left_icon);
        right = this.findViewById(R.id.right_root);
        right_text = this.findViewById(R.id.right_text);
        right_icon = this.findViewById(R.id.right_icon);
        if (left_icon_res != 0) {
            left_icon.setImageDrawable(getResources().getDrawable(left_icon_res));
        } else {
            left_icon.setImageDrawable(null);
        }


        if (right_icon_res != 0) {
            right_icon.setImageDrawable(getResources().getDrawable(right_icon_res));
        } else {
            right_icon.setImageDrawable(null);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }

        if (!TextUtils.isEmpty(leftStr)) {
            left_text.setText(leftStr);
        }

        if (!TextUtils.isEmpty(rightStr)) {
            right_text.setText(rightStr);
        }


    }

    private void initType() {
        initLeftType();
        initRightType();
    }

    private void initRightType() {
        if (right_type == 1) {
            right_text.setVisibility(VISIBLE);
            right_icon.setVisibility(GONE);
        } else {
            right_text.setVisibility(GONE);
            right_icon.setVisibility(VISIBLE);
        }

    }

    private void initLeftType() {
        if (left_type == 1) {
            left_text.setVisibility(VISIBLE);
            left_icon.setVisibility(GONE);
        } else {
            left_text.setVisibility(GONE);
            left_icon.setVisibility(VISIBLE);
        }
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setLeft_text(String leftStr) {
        this.leftStr = leftStr;
        setLeft_type(TEXT);
        left_text.setText(leftStr);
    }

    public void setRight_text(String rightStr) {
        this.rightStr = rightStr;
        setRight_type(TEXT);
        right_text.setText(rightStr);
    }

    public void setLeftIcon(@DrawableRes int res) {
        this.left_icon_res = res;
        setLeft_type(ICON);
        left_icon.setImageDrawable(getResources().getDrawable(left_icon_res));
    }

    public void setRightIcon(@DrawableRes int res) {
        this.right_icon_res = res;
        setRight_type(ICON);
        right_icon.setImageDrawable(getResources().getDrawable(left_icon_res));
    }


    public void setLeft_type(int left_type) {
        this.left_type = left_type;
        initLeftType();
    }

    public void setRight_type(int right_type) {
        this.right_type = right_type;
        initRightType();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        View view = getChildAt(0);
        setMeasuredDimension(widthMeasureSpec, view.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        view.layout(0, 0, getWidth(), view.getMeasuredHeight());
    }


    public void setStatusBar(boolean b) {
        if (b) {
            int statusBarHeight = StatusBarHelper.getStatusbarHeight(getContext());
            LayoutParams params = status_bar_space.getLayoutParams();
            params.height = statusBarHeight;
            status_bar_space.setLayoutParams(params);
            status_bar_space.setVisibility(VISIBLE);
            status_bar_space.setAlpha(0.5f);
        } else {
            status_bar_space.setVisibility(GONE);
        }
    }


}
