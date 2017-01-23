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
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by felix on 17/1/22.
 */
public class CropImageView extends ImageView {

    private static final String TAG = "CropImageView";

    private static final int CROP_TOP = 100;

    private static final int RADIUS = 16;

    private Path MASK = new Path();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CropImageView(Context context) {
        super(context);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        MASK.reset();
        MASK.addRect(new RectF(0, 0, width, height), Path.Direction.CW);
        MASK.addRoundRect(new RectF(getPaddingLeft(),
                        getPaddingTop() + CROP_TOP,
                        width - getPaddingRight(),
                        height - getPaddingBottom()),
                RADIUS, RADIUS, Path.Direction.CW);
        MASK.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw:-start");
        super.onDraw(canvas);
        Log.d(TAG, "onDraw:-end");
    }

    @Override
    public void draw(Canvas canvas) {
        int layer = canvas.saveLayer(
                0, 0,
                canvas.getWidth(), canvas.getHeight(),
                null, Canvas.ALL_SAVE_FLAG);

        super.draw(canvas);
        canvas.drawPath(MASK, mPaint);
        canvas.restoreToCount(layer);
    }
}
