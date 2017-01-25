package me.kareluo.support.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by felix on 17/1/25.
 */

public class TagView extends TextView {

    private Path mTagPath = new Path();

    private static final Paint PAINT;

    static {
        PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
        PAINT.setStyle(Paint.Style.FILL);
        PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
            mTagPath.addCircle(radius, radius, radius, Direction.CW);
        } else {
            mTagPath.addRoundRect(new RectF(0, 0, width, height), radius, radius, Direction.CW);
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
