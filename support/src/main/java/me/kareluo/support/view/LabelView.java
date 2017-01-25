package me.kareluo.support.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
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
import android.widget.CheckedTextView;

import java.util.Arrays;

import me.kareluo.support.R;
import me.kareluo.support.util.value.ValueUtils;

/**
 * Created by felix on 17/1/25.
 */

public class LabelView extends CheckedTextView {

    private int mBorderWidth;

    private ColorStateList mBorderColorList;

    private Path mLabelPath = new Path();

    private Path mBorderPath = new Path();

    private Paint mBorderPaint;

    private int[] mCacheDrawableState = new int[0];

    private static final Paint PAINT;

    static {
        PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
        PAINT.setStyle(Paint.Style.FILL);
        PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public LabelView(Context context) {
        this(context, null, 0);
    }

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LabelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabelView);
        if (a != null) {
            if (a.hasValue(R.styleable.LabelView_borderColor)) {
                mBorderColorList = a.getColorStateList(R.styleable.LabelView_borderColor);
            }
            mBorderWidth = a.getDimensionPixelSize(R.styleable.LabelView_borderWidth, 0);
            a.recycle();
        }
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        refreshBorderPaint();
    }

    @Override
    @SuppressWarnings("all")
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width < height) {
            // Invalidate size.
            setMinimumWidth(height);
            return;
        }

        int radius = height >> 1;

        mLabelPath.reset();

        if (width == height) {
            mLabelPath.addCircle(radius, radius, radius, Path.Direction.CW);
        } else {
            mLabelPath.addRoundRect(new RectF(0, 0, width, height), radius, radius, Path.Direction.CW);

        }
        mLabelPath.setFillType(Path.FillType.WINDING);

        resetBorderPath();
    }

    public void setBorderColor(int color) {
        setBorderColor(new ColorStateList(new int[][]{{android.R.attr.state_checked},
                {-android.R.attr.state_checked}}, new int[]{color, color}));
    }

    public void setBorderColor(ColorStateList stateList) {
        mBorderColorList = stateList;
        refreshDrawableState();
        postInvalidate();
    }

    public void setBorderWidth(int width) {
        mBorderWidth = width;
        resetBorderPath();
        postInvalidate();
    }

    @Override
    public void refreshDrawableState() {
        super.refreshDrawableState();
        if (shouldShowBorder()) {
            refreshBorderPaint();
        }
    }

    private void resetBorderPath() {
        if (shouldShowBorder()) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            if (width == 0 || height == 0) {
                // Invalidate size.
                return;
            }

            int radius = height >> 1;

            int offset = mBorderWidth >> 1;

            mBorderPath.reset();

            if (width == height) {
                mBorderPath.addCircle(radius, radius, radius - offset, Path.Direction.CW);
            } else {
                mBorderPath.addRoundRect(new RectF(offset, offset, width - offset, height - offset),
                        radius - offset, radius - offset, Path.Direction.CW);
            }

            refreshBorderPaint();
        }
    }

    private void refreshBorderPaint() {
        if (mBorderColorList != null) {
            if (!Arrays.equals(mCacheDrawableState, getDrawableState())) {
                mCacheDrawableState = getDrawableState();
                mBorderPaint.setColor(mBorderColorList.getColorForState(
                        getDrawableState(), Color.TRANSPARENT));
            }
        }
    }

    private boolean shouldShowBorder() {
        return mBorderWidth > 0 && mBorderColorList != null && mBorderColorList.isStateful();
    }

    @Override
    public void draw(Canvas canvas) {
        int layer = saveLayer(canvas);
        super.draw(canvas);
        canvas.drawPath(mLabelPath, PAINT);
        if (shouldShowBorder()) {
            canvas.drawPath(mBorderPath, mBorderPaint);
        }
        canvas.restoreToCount(layer);
    }

    private static int saveLayer(Canvas canvas) {
        return canvas.saveLayer(0, 0, canvas.getWidth(),
                canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
    }
}
