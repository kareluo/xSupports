package me.kareluo.support.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import me.kareluo.support.R;

/**
 * Created by felix on 17/1/23.
 */
public class CircularImageView extends ImageView {

    /**
     * The ring's color.
     */
    private int mRingColor;

    /**
     * The ring's width.
     */
    private int mRingWidth;

    /**
     * The ring's padding to 'src'
     */
    private int mRingPadding;

    private int mCenterXY, mRadius;

    /**
     * The ring's paint.
     */
    private Paint mRingPaint;

    private Path mMaskPath = new Path();

    private int mRingMode = RING_MODE_ALL;

    private static final Paint PAINT;

    /**
     * Src
     */
    public static final int RING_MODE_SRC = 1;

    /**
     * Foreground
     */
    public static final int RING_MODE_FORE = 2;

    /**
     * Src and Foreground
     */
    public static final int RING_MODE_SRC_FORE = 3;

    /**
     * Src, Foreground and Background
     */
    public static final int RING_MODE_ALL = 7;

    static {
        PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
        PAINT.setStyle(Paint.Style.FILL_AND_STROKE);
        PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public CircularImageView(Context context) {
        super(context);
        initialize(context, null);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView);
        if (a != null) {
            mRingColor = a.getColor(R.styleable.CircularImageView_ringColor, Color.TRANSPARENT);
            mRingWidth = a.getDimensionPixelSize(R.styleable.CircularImageView_ringWidth, 0);
            mRingPadding = a.getDimensionPixelSize(R.styleable.CircularImageView_ringPadding, 0);
            mRingMode = a.getInt(R.styleable.CircularImageView_ringMode, RING_MODE_ALL);
            a.recycle();
        }
        mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStrokeWidth(mRingWidth);
    }

    public void setRingColor(int color) {
        mRingColor = color;
        mRingPaint.setColor(color);
        postInvalidate();
    }

    public void setRingWidth(int width) {
        mRingWidth = width;
        mRingPaint.setStrokeWidth(width);
        resetMask();
        postInvalidate();
    }

    public void setRingPadding(int padding) {
        mRingPadding = padding;
        resetMask();
        postInvalidate();
    }

    public void setRingMode(int mode) {
        if (mode != RING_MODE_SRC && mode != RING_MODE_FORE
                && mode != RING_MODE_SRC_FORE && mode != RING_MODE_ALL) {
            throw new IllegalArgumentException("Mode is not support.");
        }
        mRingMode = mode;
        postInvalidate();
    }

    public int getRingColor() {
        return mRingColor;
    }

    public int getRingWidth() {
        return mRingWidth;
    }

    public int getRingPadding() {
        return mRingPadding;
    }

    public int getRingMode() {
        return mRingMode;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        resetMask();
    }

    private void resetMask() {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width == 0 || height == 0) {
            // return if the size is invalidate.
            return;
        }

        int padding = mRingPadding + mRingWidth;

        mCenterXY = Math.min(width, height) >> 1;
        mRadius = mCenterXY - padding;

        mMaskPath.reset();
        mMaskPath.addRect(new RectF(0, 0, width, height), Path.Direction.CW);
        mMaskPath.addCircle(mCenterXY, mCenterXY, mRadius, Path.Direction.CW);
        mMaskPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
    }

    @Override
    public void draw(Canvas canvas) {
        int layer = 0;
        if (mRingMode == RING_MODE_ALL) {
            layer = saveLayer(canvas);
        }
        super.draw(canvas);
        if (mRingMode == RING_MODE_ALL) {
            canvas.drawPath(mMaskPath, PAINT);
            canvas.drawCircle(mCenterXY, mCenterXY, mRadius + mRingPadding + (mRingWidth >> 1), mRingPaint);
            canvas.restoreToCount(layer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int layer = 0;
        if (mRingMode == RING_MODE_SRC
                || mRingMode == RING_MODE_SRC_FORE) {
            layer = saveLayer(canvas);
        }
        super.onDraw(canvas);
        if (mRingMode == RING_MODE_SRC
                || mRingMode == RING_MODE_SRC_FORE) {
            canvas.drawPath(mMaskPath, PAINT);
            canvas.drawCircle(mCenterXY, mCenterXY, mRadius + mRingPadding + (mRingWidth >> 1), mRingPaint);
            canvas.restoreToCount(layer);
        }
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        int layer = 0;
        if (mRingMode == RING_MODE_FORE
                || mRingMode == RING_MODE_SRC_FORE) {
            layer = saveLayer(canvas);
        }
        super.onDrawForeground(canvas);
        if (mRingMode == RING_MODE_FORE
                || mRingMode == RING_MODE_SRC_FORE) {
            canvas.drawPath(mMaskPath, PAINT);
            canvas.drawCircle(mCenterXY, mCenterXY, mRadius + mRingPadding + (mRingWidth >> 1), mRingPaint);
            canvas.restoreToCount(layer);
        }
    }

    private static int saveLayer(Canvas canvas) {
        return canvas.saveLayer(0, 0, canvas.getWidth(),
                canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
    }
}
