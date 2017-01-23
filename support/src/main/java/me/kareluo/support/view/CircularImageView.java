package me.kareluo.support.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by felix on 17/1/23.
 */
public class CircularImageView extends ImageView {

    private Path mMaskPath = new Path();

    private static final Paint PAINT;

    static {
        PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
        PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        resetMask();
    }

    private void resetMask() {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width == 0 || height == 0) return;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int size = Math.min(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom);

        int radius = size >> 1;

        int centerX = paddingLeft + radius;
        int centerY = paddingTop + radius;

        mMaskPath.reset();
        mMaskPath.addRect(new RectF(paddingLeft, paddingTop, width + paddingLeft, height + paddingTop), Path.Direction.CW);
        mMaskPath.addCircle(centerX, centerY, radius, Path.Direction.CW);
        mMaskPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
    }

    @Override
    public void draw(Canvas canvas) {
        int layer = canvas.saveLayer(
                0, 0,
                canvas.getWidth(), canvas.getHeight(),
                null, Canvas.ALL_SAVE_FLAG);

        super.draw(canvas);
        canvas.drawPath(mMaskPath, PAINT);
        canvas.restoreToCount(layer);
    }
}
