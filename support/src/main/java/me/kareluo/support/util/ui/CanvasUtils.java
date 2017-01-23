package me.kareluo.support.util.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;

/**
 * Created by felix on 16/6/14.
 */
public class CanvasUtils {

    private static PorterDuffXfermode MODE = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    public static void mask(Canvas canvas, Paint paint, Bitmap mask, float left, float top) {
        RectF bounds = new RectF(left, top, canvas.getWidth(), canvas.getHeight());

        canvas.save();
//        canvas.saveLayer(bounds.round();, paint);
        Xfermode xfermode = paint.getXfermode();
        paint.setXfermode(MODE);

        canvas.drawBitmap(mask, null, new RectF(left, top, canvas.getWidth(), canvas.getHeight()), paint);
//        canvas.drawBitmap(mask, left, top, paint);
        paint.setXfermode(xfermode);
        canvas.restore();
    }

    public static void mask(Canvas canvas, Paint paint, Bitmap mask) {
        mask(canvas, paint, mask, 0, 0);
    }
}
