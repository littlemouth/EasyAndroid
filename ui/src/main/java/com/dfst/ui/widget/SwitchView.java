package com.dfst.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.dfst.ui.R;


/**
 * Created by yanfei on 2016-08-23.
 */
public class SwitchView extends View implements View.OnClickListener {

    private Paint borderPaint, innerPaint;

    private float scale;

    private int count;

    private int height, width, paddingLeft, paddingTop, paddingRight, paddingBottom;

    private float centerX, centerY, centerLeftX, centerLeftY, centerRightX, centerRightY;

    private RectF borderRectF, switchRectF;

    private float borderRadius, offRadius, onRadius;

    private float offVariableLength, offTranslation, onVariableLength;

    private boolean on;

    private SwitchUpdateAnimatorListener switchUpdateAnimatorListener;

    private OnSwitchChangedListener listener;

    private int borderColor, onColor, offColor;

    public SwitchView(Context context) {
        super(context);
        init(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(Color.LTGRAY);
        borderPaint.setStrokeWidth(2);

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.FILL);

        borderRectF = new RectF();
        switchRectF = new RectF();

        switchUpdateAnimatorListener = new SwitchUpdateAnimatorListener();

        setOnClickListener(this);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchView);
            borderColor = typedArray.getColor(R.styleable.SwitchView_borderColor, Color.GRAY);
            offColor = typedArray.getColor(R.styleable.SwitchView_offColor, Color.GRAY);
            onColor = typedArray.getColor(R.styleable.SwitchView_onColor, Color.GREEN);
            on = typedArray.getBoolean(R.styleable.SwitchView_on, false);
            if (on)
                scale = 2f;
            else
                scale = 0;
        } else {
            borderColor = Color.GRAY;
            offColor = Color.GRAY;
            onColor = Color.GREEN;
            scale = 0;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        centerX = (width - paddingLeft - paddingRight) / 2 + paddingLeft;
        centerY = (height - paddingTop - paddingBottom) / 2 + paddingTop;
        borderRadius = (height - paddingTop - paddingBottom) / 2;
        centerLeftX = paddingLeft + borderRadius;
        centerLeftY = centerY;
        centerRightX = width - paddingRight - borderRadius;
        centerRightY = centerY;
        offRadius = (height - paddingTop - paddingBottom) / 10;
        onRadius = offRadius;
        offVariableLength = offRadius * 3f;
        onVariableLength = offRadius * 2.5f;
        offTranslation = centerRightX - centerLeftX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
        drawSwitch(canvas);
    }

    private void drawBorder(Canvas canvas) {
        innerPaint.setColor(borderColor);
        borderRectF.set(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        canvas.drawRoundRect(borderRectF, borderRadius, borderRadius, borderPaint);
    }

    private void drawSwitch(Canvas canvas) {
        if (scale < 1) {
            innerPaint.setColor(offColor);
            switchRectF.set(centerLeftX + scale * offTranslation - offRadius - offVariableLength * (1f - scale) / 2,
                    centerLeftY - offRadius,
                    centerLeftX + scale * offTranslation + offRadius + offVariableLength * (1f - scale) / 2,
                    centerLeftY + offRadius);
            canvas.drawRoundRect(switchRectF, offRadius, offRadius, innerPaint);
        }

        if (scale >= 1) {
            innerPaint.setColor(onColor);
            canvas.drawCircle(centerRightX, centerRightY, onRadius + (scale - 1) * onVariableLength, innerPaint);
        }
    }

    @Override
    public void onClick(View v) {
        if (on)
            on2Off();
        else
            off2On();

        if (listener != null) {
            listener.onSwitchChanged(this, on);
        }
    }

    private void off2On() {
        on = true;
        ObjectAnimator offAnimator = ObjectAnimator.ofFloat(this, "scale", scale, 2f);
        offAnimator.setDuration(200);
        offAnimator.addUpdateListener(switchUpdateAnimatorListener);;
        offAnimator.start();
    }

    private void on2Off() {
        on = false;
        ObjectAnimator offAnimator = ObjectAnimator.ofFloat(this, "scale", scale, 0);
        offAnimator.setDuration(200);
        offAnimator.addUpdateListener(switchUpdateAnimatorListener);
        offAnimator.start();
    }


    public void setBorderColor(int color) {
        this.borderColor = color;
        invalidate();
    }

    public void setOnColor(int color) {
        this.onColor = color;
        invalidate();
    }

    public void setOffColor(int color) {
        this.offColor = color;
        invalidate();
    }

    public void setOn(boolean on) {
        this.on = on;
        invalidate();
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setOnSwitchChangedListener(OnSwitchChangedListener listener) {
        this.listener = listener;
    }

    public interface OnSwitchChangedListener {
        void onSwitchChanged(SwitchView view, boolean on);
    }

    class SwitchUpdateAnimatorListener implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            postInvalidate();
        }
    }

    private class SimpleAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
