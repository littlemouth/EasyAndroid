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
 * 圆形进度view
 */
public class ProgressWheelView extends View{
    private Context context;
    private RectF innerRect;
    private RectF outerRect;

    private float innerRadius;
    private float outerRadius;

    private int width;
    private int height;
    private int innerColor;
    private int outerColor;
    private int progressColor;
    private int textColor;

    private Paint innerPaint;
    private Paint outerPaint;
    private Paint progressPaint;
    private Paint textPaint;

    /**进度*/
    private int progress;
    /**字体大小*/
    private float textSize;
    /**是否文字显示进度*/
    private boolean isTextShow;

    public ProgressWheelView(Context context) {
        super(context);
        this.context = context;
        init();
        setPaint();
    }

    public ProgressWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        /**自定义数据加载*/
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressWheelView, 0, 0);
        try {
            innerRadius = typedArray.getDimension(R.styleable.ProgressWheelView_innerRadius, DensityUtil.dip2px(context, 30f));
            outerRadius = typedArray.getDimension(R.styleable.ProgressWheelView_outerRadius, DensityUtil.dip2px(context,32));
            innerColor = typedArray.getInt(R.styleable.ProgressWheelView_innerColor, Color.WHITE);
            outerColor = typedArray.getInt(R.styleable.ProgressWheelView_outerColor, Color.LTGRAY);
            progressColor = typedArray.getInt(R.styleable.ProgressWheelView_progressColor, Color.GREEN);
            textColor = typedArray.getInt(R.styleable.ProgressWheelView_textColor, Color.BLACK);
            textSize = typedArray.getDimension(R.styleable.ProgressWheelView_textSize, DensityUtil.dip2px(context, 14f));
            isTextShow = typedArray.getBoolean(R.styleable.ProgressWheelView_isShowText, true);

            progress = 0;
            setPaint();
        }finally {
            typedArray.recycle();
        }
    }

    private void init(){
        progress = 0;

        innerRadius = DensityUtil.dip2px(context,30f);
        outerRadius = DensityUtil.dip2px(context,32f);
        innerColor = Color.WHITE;
        outerColor = Color.LTGRAY;
        progressColor = Color.GREEN;
        textColor = Color.BLACK;
        textSize = DensityUtil.dip2px(context, 14f);
        isTextShow = true;
    }

    private void setPaint(){
        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStrokeWidth(2);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setColor(innerColor);

        outerPaint = new Paint();
        outerPaint.setAntiAlias(true);
        outerPaint.setStrokeWidth(2);
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(outerColor);

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(2);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setColor(progressColor);

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

        int wrap_content_width = (int) (2*outerRadius+getPaddingLeft()+getPaddingRight());
        int wrap_content_height = (int) (2*outerRadius +getPaddingBottom()+getPaddingTop());
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

        outerRect = new RectF((float)(getPaddingLeft()),(float)(getPaddingTop()),
                (float)(width-getPaddingRight()),(float)(height-getPaddingBottom()));
        innerRect =  new RectF((outerRect.left+(outerRadius-innerRadius)),
                                 (outerRect.top+(outerRadius-innerRadius)),
                                 (outerRect.right-(outerRadius-innerRadius)),
                                 (outerRect.bottom-(outerRadius-innerRadius)));

    }

    public void onDraw(Canvas canvas){
        setPaint();
        /**原始外圆*/
        canvas.drawArc(outerRect,360,360,false,outerPaint);
        /**进度圆*/
        canvas.drawArc(outerRect,-90,progress,true,progressPaint);
        /**原始内圆*/
        canvas.drawArc(innerRect,360,360,false,innerPaint);
        /**绘制文字进度,在内圆内*/
        if(isTextShow){
            int index = (int)(progress/360.0*100);
            String text = index + "%";
            float textWidth =  textPaint.measureText(text);
            float textHeight = textPaint.descent()-textPaint.ascent();
            float verticalTextOffset = (textHeight / 2) - textPaint.descent();
            float x = (float) (innerRect.left+(innerRect.right-innerRect.left)*0.5-textWidth*0.5);
            float y = (float)(innerRect.top+(innerRect.bottom-innerRect.top)*0.5+verticalTextOffset);
            canvas.drawText(text, x, y, textPaint);
        }

    }

    public float getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(float innerRadius) {
        this.innerRadius = DensityUtil.dip2px(context, innerRadius);
    }

    public float getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(float outerRadius) {
        this.outerRadius = DensityUtil.dip2px(context, outerRadius);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setInnerColor(int innerColor) {
        this.innerColor = innerColor;
    }

    public void setOuterColor(int outerColor) {
        this.outerColor = outerColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = DensityUtil.dip2px(context, textSize);
    }

    public void setIsTextShow(boolean isTextShow) {
        this.isTextShow = isTextShow;
    }


}
