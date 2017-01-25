package me.kareluo.support.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
 * Created by felix on 17/1/22.
 */
public class CropImageView extends ImageView {

    private static final String TAG = "CropImageView";

    private int mCropLeft, mCropTop, mCropRight, mCropBottom;

    private int mRadiusLeftTop, mRadiusRightTop, mRadiusLeftBottom, mRadiusRightBottom;

    private Path mMaskPath = new Path();

    private static final Paint PAINT;

    static {
        PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
        PAINT.setStyle(Paint.Style.FILL);
        PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public CropImageView(Context context) {
        this(context, null, 0);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CropImageView);
        if (a != null) {
            int crop = a.getDimensionPixelSize(R.styleable.CropImageView_crop, 0);
            mCropLeft = mCropTop = mCropRight = mCropBottom = crop;

            if (a.hasValue(R.styleable.CropImageView_cropLeft)) {
                mCropLeft = a.getDimensionPixelSize(R.styleable.CropImageView_cropLeft, 0);
            }

            if (a.hasValue(R.styleable.CropImageView_cropTop)) {
                mCropTop = a.getDimensionPixelSize(R.styleable.CropImageView_cropTop, 0);
            }

            if (a.hasValue(R.styleable.CropImageView_cropRight)) {
                mCropRight = a.getDimensionPixelSize(R.styleable.CropImageView_cropRight, 0);
            }

            if (a.hasValue(R.styleable.CropImageView_cropBottom)) {
                mCropBottom = a.getDimensionPixelSize(R.styleable.CropImageView_cropBottom, 0);
            }

            int radius = a.getDimensionPixelSize(R.styleable.CropImageView_radius, 0);
            mRadiusLeftTop = mRadiusRightTop = mRadiusRightBottom = mRadiusLeftBottom = radius;

            if (a.hasValue(R.styleable.CropImageView_radiusLeftTop)) {
                mRadiusLeftTop = a.getDimensionPixelSize(R.styleable.CropImageView_radiusLeftTop, 0);
            }

            if (a.hasValue(R.styleable.CropImageView_radiusRightTop)) {
                mRadiusRightTop = a.getDimensionPixelSize(R.styleable.CropImageView_radiusRightTop, 0);
            }

            if (a.hasValue(R.styleable.CropImageView_radiusLeftBottom)) {
                mRadiusLeftBottom = a.getDimensionPixelSize(R.styleable.CropImageView_radiusLeftBottom, 0);
            }

            if (a.hasValue(R.styleable.CropImageView_radiusRightBottom)) {
                mRadiusRightBottom = a.getDimensionPixelSize(R.styleable.CropImageView_radiusRightBottom, 0);
            }

            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        resetMaskPath();
    }

    private void resetMaskPath() {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width == 0 || height == 0) {
            // Invalidate size.
            return;
        }

        mMaskPath.reset();
        mMaskPath.addRect(new RectF(0, 0, width, height), Path.Direction.CW);
        mMaskPath.addRoundRect(
                new RectF(
                        getPaddingLeft() + mCropLeft,
                        getPaddingTop() + mCropTop,
                        width - getPaddingRight() - mCropRight,
                        height - getPaddingBottom() - mCropBottom
                ),
                new float[]{
                        mRadiusLeftTop, mRadiusLeftTop,
                        mRadiusRightTop, mRadiusRightTop,
                        mRadiusRightBottom, mRadiusRightBottom,
                        mRadiusLeftBottom, mRadiusLeftBottom
                }, Path.Direction.CW);
        mMaskPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
    }

    @Override
    public void draw(Canvas canvas) {
        int layer = saveLayer(canvas);
        super.draw(canvas);
        canvas.drawPath(mMaskPath, PAINT);
        canvas.restoreToCount(layer);
    }

    private static int saveLayer(Canvas canvas) {
        return canvas.saveLayer(0, 0, canvas.getWidth(),
                canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
    }
}
