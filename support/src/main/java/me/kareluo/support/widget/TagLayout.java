package me.kareluo.support.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by felix on 17/1/25.
 */

public class TagLayout extends FrameLayout {

    private Path mTagPath = new Path();

    private static final Paint PAINT;

    static {
        PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
        PAINT.setStyle(Paint.Style.FILL);
        PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public TagLayout(Context context) {
        this(context, null, 0);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    private void initialize(Context context) {
        Drawable drawable = getBackground();
        if (drawable == null) {
            setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    @SuppressWarnings("all")
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        // TagView request width bigger than or equals to height.
        if (width < height) {
            setMinimumWidth(height);
            return;
        }

        int radius = height >> 1;

        mTagPath.reset();
        if (width == height) {
            mTagPath.addCircle(radius, radius, radius, Path.Direction.CW);
        } else {
            mTagPath.addRoundRect(new RectF(0, 0, width, height), radius, radius, Path.Direction.CW);
        }
        mTagPath.setFillType(Path.FillType.WINDING);
    }

    @Override
    public void draw(Canvas canvas) {
        int layer = saveLayer(canvas);
        super.draw(canvas);
        canvas.drawPath(mTagPath, PAINT);
        canvas.restoreToCount(layer);
    }

    private static int saveLayer(Canvas canvas) {
        return canvas.saveLayer(0, 0, canvas.getWidth(),
                canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
    }
}
