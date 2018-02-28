package com.example.duangniu000.test2.Util.RoundDrawable;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.Gravity;

/**
 * A Drawable that wraps a bitmap and can be drawn with rounded corners. You can create a
 * RoundedBitmapDrawable from a file path, an input stream, or from a
 * {@link Bitmap} object.
 * <p>
 * Also see the {@link Bitmap} class, which handles the management and
 * transformation of raw bitmap graphics, and should be used when drawing to a
 * {@link Canvas}.
 * </p>
 */
@RequiresApi(9)
@TargetApi(9)
public abstract class RoundedBitmapDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS =
            Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG;
    final Bitmap mBitmap;
    private int mTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
    private int mGravity = Gravity.FILL;
    private final Paint mPaint = new Paint(DEFAULT_PAINT_FLAGS);
    private final BitmapShader mBitmapShader;
    private final Matrix mShaderMatrix = new Matrix();


    final Rect mDstRect = new Rect();   // Gravity.apply() sets this
    private final RectF mDstRectF = new RectF();

    private boolean mApplyGravity = true;
    private boolean mIsCircular;

    // These are scaled to match the target density.
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float mCornerRadius;


    //圆角的半径，依次为左上角 xy 半径，右上角，右下角，左下角
    private float[] rids = new float[8];

    /**
     * Returns the paint used to render this drawable.
     */
    public final Paint getPaint() {
        return mPaint;
    }

    /**
     * Returns the bitmap used by this drawable to render. May be null.
     */
    public final Bitmap getBitmap() {
        return mBitmap;
    }

    private void computeBitmapSize() {
        mBitmapWidth = mBitmap.getScaledWidth(mTargetDensity);
        mBitmapHeight = mBitmap.getScaledHeight(mTargetDensity);
    }

    /**
     * Set the density scale at which this drawable will be rendered. This
     * method assumes the drawable will be rendered at the same density as the
     * specified canvas.
     *
     * @param canvas The Canvas from which the density scale must be obtained.
     * @see Bitmap#setDensity(int)
     * @see Bitmap#getDensity()
     */
    public void setTargetDensity(Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    /**
     * Set the density scale at which this drawable will be rendered.
     *
     * @param metrics The DisplayMetrics indicating the density scale for this drawable.
     * @see Bitmap#setDensity(int)
     * @see Bitmap#getDensity()
     */
    public void setTargetDensity(DisplayMetrics metrics) {
        setTargetDensity(metrics.densityDpi);
    }

    /**
     * Set the density at which this drawable will be rendered.
     *
     * @param density The density scale for this drawable.
     * @see Bitmap#setDensity(int)
     * @see Bitmap#getDensity()
     */
    public void setTargetDensity(int density) {
        if (mTargetDensity != density) {
            mTargetDensity = density == 0 ? DisplayMetrics.DENSITY_DEFAULT : density;
            if (mBitmap != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }

    /**
     * Get the gravity used to position/stretch the bitmap within its bounds.
     *
     * @return the gravity applied to the bitmap
     * @see Gravity
     */
    public int getGravity() {
        return mGravity;
    }

    /**
     * Set the gravity used to position/stretch the bitmap within its bounds.
     *
     * @param gravity the gravity
     * @see Gravity
     */
    public void setGravity(int gravity) {
        if (mGravity != gravity) {
            mGravity = gravity;
            mApplyGravity = true;
            invalidateSelf();
        }
    }

    /**
     * Enables or disables the mipmap hint for this drawable's bitmap.
     * See {@link Bitmap#setHasMipMap(boolean)} for more information.
     * <p>
     * If the bitmap is null, or the current API version does not support setting a mipmap hint,
     * calling this method has no effect.
     *
     * @param mipMap True if the bitmap should use mipmaps, false otherwise.
     * @see #hasMipMap()
     */
    public void setMipMap(boolean mipMap) {
        throw new UnsupportedOperationException(); // must be overridden in subclasses
    }

    /**
     * Indicates whether the mipmap hint is enabled on this drawable's bitmap.
     *
     * @return True if the mipmap hint is set, false otherwise. If the bitmap
     * is null, this method always returns false.
     * @see #setMipMap(boolean)
     */
    public boolean hasMipMap() {
        throw new UnsupportedOperationException(); // must be overridden in subclasses
    }

    /**
     * Enables or disables anti-aliasing for this drawable. Anti-aliasing affects
     * the edges of the bitmap only so it applies only when the drawable is rotated.
     *
     * @param aa True if the bitmap should be anti-aliased, false otherwise.
     * @see #hasAntiAlias()
     */
    public void setAntiAlias(boolean aa) {
        mPaint.setAntiAlias(aa);
        invalidateSelf();
    }

    /**
     * Indicates whether anti-aliasing is enabled for this drawable.
     *
     * @return True if anti-aliasing is enabled, false otherwise.
     * @see #setAntiAlias(boolean)
     */
    public boolean hasAntiAlias() {
        return mPaint.isAntiAlias();
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        mPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override
    public void setDither(boolean dither) {
        mPaint.setDither(dither);
        invalidateSelf();
    }

    void gravityCompatApply(int gravity, int bitmapWidth, int bitmapHeight,
                            Rect bounds, Rect outRect) {
        throw new UnsupportedOperationException();
    }

    void updateDstRect() {
        if (mApplyGravity) {
            if (mIsCircular) {
                final int minDimen = Math.min(mBitmapWidth, mBitmapHeight);
                gravityCompatApply(mGravity, minDimen, minDimen, getBounds(), mDstRect);

                // inset the drawing rectangle to the largest contained square,
                // so that a circle will be drawn
                final int minDrawDimen = Math.min(mDstRect.width(), mDstRect.height());
                final int insetX = Math.max(0, (mDstRect.width() - minDrawDimen) / 2);
                final int insetY = Math.max(0, (mDstRect.height() - minDrawDimen) / 2);
                mDstRect.inset(insetX, insetY);
                mCornerRadius = 0.5f * minDrawDimen;
            } else {
                gravityCompatApply(mGravity, mBitmapWidth, mBitmapHeight, getBounds(), mDstRect);
            }
            mDstRectF.set(mDstRect);

            if (mBitmapShader != null) {
                // setup shader matrix
                mShaderMatrix.setTranslate(mDstRectF.left, mDstRectF.top);
                mShaderMatrix.preScale(
                        mDstRectF.width() / mBitmap.getWidth(),
                        mDstRectF.height() / mBitmap.getHeight());
                mBitmapShader.setLocalMatrix(mShaderMatrix);
                mPaint.setShader(mBitmapShader);
            }

            mApplyGravity = false;
        }
    }


    Paint boderPaint;

    public void setboderColor(int color) {
        if (boderPaint == null) {
            boderPaint = new Paint();
            boderPaint.setAntiAlias(true);
            boderPaint.setStrokeWidth(2);
            boderPaint.setStyle(Paint.Style.STROKE);
        }
        boderPaint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        final Bitmap bitmap = mBitmap;
        if (bitmap == null) {
            return;
        }

        updateDstRect();
        if (mPaint.getShader() == null) {
            canvas.drawBitmap(bitmap, null, mDstRect, mPaint);
        } else {
            Path path = new Path();
            path.addRoundRect(mDstRectF, rids, Path.Direction.CW);
            canvas.clipPath(path);
            canvas.drawRoundRect(mDstRectF, mCornerRadius, mCornerRadius, mPaint);
            //边线
            if (boderPaint != null) {
                canvas.drawRoundRect(mDstRectF, rids[0] + 2, rids[0] + 2, boderPaint);
            }


        }
    }

    @Override
    public void setAlpha(int alpha) {
        final int oldAlpha = mPaint.getAlpha();
        if (alpha != oldAlpha) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return mPaint.getAlpha();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
        invalidateSelf();
    }

    public ColorFilter getColorFilter() {
        return mPaint.getColorFilter();
    }

    /**
     * Sets the image shape to circular.
     * <p>This overwrites any calls made to {@link #setCornerRadius(float)} so far.</p>
     */
    public void setCircular(boolean circular) {
        mIsCircular = circular;
        mApplyGravity = true;
        if (circular) {
            updateCircularCornerRadius();
            mPaint.setShader(mBitmapShader);
            invalidateSelf();
        } else {
            setCornerRadius(0);
        }
    }

    private void updateCircularCornerRadius() {
        final int minCircularSize = Math.min(mBitmapHeight, mBitmapWidth);
        mCornerRadius = minCircularSize / 2;
    }

    /**
     * @return <code>true</code> if the image is circular, else <code>false</code>.
     */
    public boolean isCircular() {
        return mIsCircular;
    }

    /**
     * Sets the corner radius to be applied when drawing the bitmap.
     */
    private void setCornerRadius(float cornerRadius) {
        if (mCornerRadius == cornerRadius) return;

        mIsCircular = false;
        if (isGreaterThanZero(cornerRadius)) {
            mPaint.setShader(mBitmapShader);
        } else {
            mPaint.setShader(null);
        }
        mCornerRadius = cornerRadius;

        invalidateSelf();
    }

    public void setRadius(float tl, float tr, float bl, float br) {
        mIsCircular = false;
        mPaint.setShader(mBitmapShader);
        rids[0] = rids[1] = tl;
        rids[2] = rids[3] = tr;
        rids[4] = rids[5] = bl;
        rids[6] = rids[7] = br;
        invalidateSelf();
    }

    public void setRadius(float cornerRadius) {
        mIsCircular = false;
        if (isGreaterThanZero(cornerRadius)) {
            mPaint.setShader(mBitmapShader);
        } else {
            mPaint.setShader(null);
        }
        rids[0] = rids[1] = rids[2] = rids[3] = rids[4] = rids[5] = rids[6] = rids[7] = cornerRadius;
        invalidateSelf();
    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (mIsCircular) {
            updateCircularCornerRadius();
        }
        mApplyGravity = true;
    }

    /**
     * @return The corner radius applied when drawing the bitmap.
     */
    public float getCornerRadius() {
        return mCornerRadius;
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmapHeight;
    }

    @Override
    public int getOpacity() {
        if (mGravity != Gravity.FILL || mIsCircular) {
            return PixelFormat.TRANSLUCENT;
        }
        Bitmap bm = mBitmap;
        return (bm == null
                || bm.hasAlpha()
                || mPaint.getAlpha() < 255
                || isGreaterThanZero(mCornerRadius))
                ? PixelFormat.TRANSLUCENT : PixelFormat.OPAQUE;
    }

    RoundedBitmapDrawable(Resources res, Bitmap bitmap) {
        if (res != null) {
            mTargetDensity = res.getDisplayMetrics().densityDpi;
        }

        mBitmap = bitmap;
        if (mBitmap != null) {
            computeBitmapSize();
            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        } else {
            mBitmapWidth = mBitmapHeight = -1;
            mBitmapShader = null;
        }
    }

    private static boolean isGreaterThanZero(float toCompare) {
        return toCompare > 0.05f;
    }

}