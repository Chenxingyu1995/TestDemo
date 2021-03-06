package com.example.duangniu000.test2.Util.RoundDrawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;

@RequiresApi(21)
class RoundedBitmapDrawable21 extends RoundedBitmapDrawable {
    protected RoundedBitmapDrawable21(Resources res, Bitmap bitmap) {
        super(res, bitmap);
    }

    @Override
    public void getOutline(Outline outline) {
        updateDstRect();
        outline.setRoundRect(mDstRect, getCornerRadius());
    }

    @Override
    public void setMipMap(boolean mipMap) {
        if (mBitmap != null) {
            mBitmap.setHasMipMap(mipMap);
            invalidateSelf();
        }
    }

    @Override
    public boolean hasMipMap() {
        return mBitmap != null && mBitmap.hasMipMap();
    }

    @Override
    void gravityCompatApply(int gravity, int bitmapWidth, int bitmapHeight,
                            Rect bounds, Rect outRect) {
        Gravity.apply(gravity, bitmapWidth, bitmapHeight,
                bounds, outRect, View.LAYOUT_DIRECTION_LTR);
    }
}
