package com.example.duangniu000.test2.Util.RoundDrawable;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;

/**
 * Constructs {@link android.support.v4.graphics.drawable.RoundedBitmapDrawable RoundedBitmapDrawable} objects,
 * either from Bitmaps directly, or from streams and files.
 */
public final class RoundedBitmapDrawableFactory {
    private static final String TAG = "RoundedBitmapDrawableFactory";

    private static class DefaultRoundedBitmapDrawable extends RoundedBitmapDrawable {
        DefaultRoundedBitmapDrawable(Resources res, Bitmap bitmap) {
            super(res, bitmap);
        }

        @Override
        public void setMipMap(boolean mipMap) {
            if (mBitmap != null) {
                BitmapCompat.setHasMipMap(mBitmap, mipMap);
                invalidateSelf();
            }
        }

        @Override
        public boolean hasMipMap() {
            return mBitmap != null && BitmapCompat.hasMipMap(mBitmap);
        }

        @Override
        void gravityCompatApply(int gravity, int bitmapWidth, int bitmapHeight,
                                Rect bounds, Rect outRect) {
            GravityCompat.apply(gravity, bitmapWidth, bitmapHeight,
                    bounds, outRect, ViewCompat.LAYOUT_DIRECTION_LTR);
        }
    }

    /**
     * Returns a new drawable by creating it from a bitmap, setting initial target density based on
     * the display metrics of the resources.
     */
    public static RoundedBitmapDrawable create(Resources res, Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new RoundedBitmapDrawable21(res, bitmap);
        }
        return new DefaultRoundedBitmapDrawable(res, bitmap);
    }

    /**
     * Returns a new drawable, creating it by opening a given file path and decoding the bitmap.
     */
    public static RoundedBitmapDrawable create(Resources res, String filepath) {
        final RoundedBitmapDrawable drawable = create(res, BitmapFactory.decodeFile(filepath));
        if (drawable.getBitmap() == null) {
            Log.w(TAG, "RoundedBitmapDrawable cannot decode " + filepath);
        }
        return drawable;
    }


    /**
     * Returns a new drawable, creating it by decoding a bitmap from the given input stream.
     */
    @SuppressLint("LongLogTag")
    public static RoundedBitmapDrawable create(Resources res, java.io.InputStream is) {
        final RoundedBitmapDrawable drawable = create(res, BitmapFactory.decodeStream(is));
        if (drawable.getBitmap() == null) {
            Log.w(TAG, "RoundedBitmapDrawable cannot decode " + is);
        }
        return drawable;
    }

    private RoundedBitmapDrawableFactory() {
    }
}
