package com.dfst.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by yanfei on 2016-12-22.
 */
public class AutoSizeImageView extends ImageView {
    private double scale;
    public AutoSizeImageView(Context context) {
        super(context);
    }

    public AutoSizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (scale > 0) {
            height = (int) Math.ceil(width * scale);
            setMeasuredDimension(width, height);
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        if (bitmap != null && bitmap.getWidth() != 0) {
            scale = new BigDecimal(bitmap.getHeight()).
                    divide(new BigDecimal(bitmap.getWidth()), 2, RoundingMode.HALF_UP).doubleValue();
        }
    }
}
