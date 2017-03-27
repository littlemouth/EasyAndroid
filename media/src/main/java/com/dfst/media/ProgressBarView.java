package com.dfst.media;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.dfst.media.util.DensityUtil;


/**
 * @autor lilinlin@supcon.com
 * date 2016/05/04
 * 条形进度view
 */
public class ProgressBarView extends View {
    private Context context;
    private RectF barBgRect;
    private RectF barProgressRect;

    private int width;
    private int height;

    private float barWidth;
    private float barHeight;

    private Paint bgPaint;
    private Paint progressPaint;
    private Paint textPaint;

    private int progressColor;
    private int progressBackgroudColor;
    private int textColor;
    /**进度*/
    private int progress=0;
    /**最大进度*/
    private int max=100;
    /**矩形圆角半径*/
    private float xRadius;
    private float yRadius;
    /**字体大小*/
    private float textSize;
    /**是否文字显示进度*/
    private boolean isTextShow;
    /**运行标志位，在onMeasure运行后再运行*/
    private boolean isGo = false;



    public ProgressBarView(Context context) {
        super(context);
        this.context = context;
        init();
        setPaint();
        invalidate();
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressBarView, 0, 0);
        try {
            progressColor  = typedArray.getColor(R.styleable.ProgressBarView_progressColor, Color.WHITE);
            progressBackgroudColor  = typedArray.getColor(R.styleable.ProgressBarView_backgroundColor, Color.GRAY);
            textColor = typedArray.getColor(R.styleable.ProgressBarView_textColor, Color.BLACK);

            max = typedArray.getInt(R.styleable.ProgressBarView_progressMax, 100);
            xRadius = typedArray.getDimension(R.styleable.ProgressBarView_xRadius, DensityUtil.dip2px(context, 5f));
            yRadius = typedArray.getDimension(R.styleable.ProgressBarView_yRadius, DensityUtil.dip2px(context,5f));
            textSize = typedArray.getDimension(R.styleable.ProgressBarView_textSize, DensityUtil.dip2px(context,10f));
            isTextShow = typedArray.getBoolean(R.styleable.ProgressBarView_isShowText, true);

            barWidth = typedArray.getDimension(R.styleable.ProgressBarView_barWidth,
                    DensityUtil.dip2px(context, 200f));
            barHeight = typedArray.getDimension(R.styleable.ProgressBarView_barHeight,
                    DensityUtil.dip2px(context,8f));

            setPaint();
        }finally {
            typedArray.recycle();
        }
    }
    private void init(){
        progressColor   =  Color.WHITE;
        progressBackgroudColor         =  Color.GRAY;
        textColor       =  Color.BLACK;

        max = 100;
        isTextShow = true;
        xRadius = DensityUtil.dip2px(context,5f);
        yRadius = DensityUtil.dip2px(context,5f);
        textSize = DensityUtil.dip2px(context,10f);

        barWidth = DensityUtil.dip2px(context,200f);
        barHeight = DensityUtil.dip2px(context,8f);
    }
    private void setPaint(){
        progress = 0;

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(2);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setColor(progressColor);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(2);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(progressBackgroudColor);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(2);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**设置wrap_content*/
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int wrap_content_width = (int) (barWidth+getPaddingLeft()+getPaddingRight());
        int wrap_content_height = (int) (barHeight+getPaddingTop()+getPaddingBottom());

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(wrap_content_width,wrap_content_height);
            width = wrap_content_width;
            height = wrap_content_height;
        }else if(widthSpecMode == MeasureSpec.AT_MOST ){
            setMeasuredDimension(wrap_content_width,heightSpecSize);
            width = wrap_content_width;
            height = heightSpecSize;
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,wrap_content_height);
            width = widthSpecSize;
            height = wrap_content_height;
        }else{
            width = widthSpecSize;
            height = heightSpecSize;
        }

        barBgRect = new RectF(getPaddingLeft(),getPaddingTop(),width-getPaddingRight(),height-getPaddingBottom());
        barProgressRect = new RectF(barBgRect.left,barBgRect.top,barBgRect.left,barBgRect.bottom);
        isGo = true;
    }

    public void onDraw(Canvas canvas){
        float step = (float) (progress * 1.0 * (barBgRect.right - barBgRect.left) / max);
        barProgressRect.left = barBgRect.left;
        barProgressRect.top = barBgRect.top;
        barProgressRect.right = barBgRect.left + step;
        barProgressRect.bottom = barBgRect.bottom;

        canvas.drawRoundRect(barBgRect, xRadius, yRadius, bgPaint);
        canvas.drawRoundRect(barProgressRect, xRadius, yRadius, progressPaint);
        /**绘制文字进度,在内圆内*/
        if(isTextShow){
            int index = (int)(progress*100.0/max);
            String text = index + "%";
            float textWidth =  textPaint.measureText(text);
            float textHeight = textPaint.descent()-textPaint.ascent();
            float verticalTextOffset = (textHeight / 2) - textPaint.descent();
            float x = barProgressRect.right;
            if((barProgressRect.right + textWidth) > barBgRect.right){
                x = barBgRect.right - textWidth;
            }
            float y = (float)(barProgressRect.top+(barProgressRect.bottom-barProgressRect.top)*0.5+verticalTextOffset);
            canvas.drawText(text, x, y, textPaint);
        }
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if(progress > max){
            this.progress = max;
        }else {
            this.progress = progress;
        }
        postInvalidate();

    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public float getxRadius() {
        return xRadius;
    }

    public void setxRadius(float xRadius) {
        this.xRadius = DensityUtil.dip2px(context,xRadius);
    }

    public float getyRadius() {
        return yRadius;
    }

    public void setyRadius(float yRadius) {
        this.yRadius = DensityUtil.dip2px(context,yRadius);
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        setPaint();
    }

    public void setProgressBackgroudColor(int progressBackgroudColor) {
        this.progressBackgroudColor = progressBackgroudColor;
        setPaint();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        setPaint();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = DensityUtil.dip2px(context,textSize);
        setPaint();
    }

    public boolean isTextShow() {
        return isTextShow;
    }

    public void setIsTextShow(boolean isTextShow) {
        this.isTextShow = isTextShow;
    }

    public float getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(float barWidth) {
        this.barWidth = DensityUtil.dip2px(context,barWidth);
    }

    public float getBarHeight() {
        return barHeight;
    }

    public void setBarHeight(float barHeight) {
        this.barHeight = DensityUtil.dip2px(context,barHeight);
    }
}

