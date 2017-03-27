package com.dfst.media;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


/**
 * @author lilinlin@supcon.com
 * date 2016/04/29
 * 绘制加载动画view
 */
public class LoadingView extends View {
    private int width;
    private int height;
    /**圆圈颜色*/
    private int color1 = Color.WHITE;
    private int color2 = Color.WHITE;
    private int color3 = Color.WHITE;
    /**圆圈半径*/
    private int radius1 = 11;
    private int radius2 = 10;
    private int radius3 = 9;
    /**滚动时间*/
    private int times = 300;
    /**关闭标志*/
    private static boolean isGo = true;
    ThreadChangeColor thread;

    public LoadingView(Context context) {
        super(context);
        thread = new ThreadChangeColor();
        thread.start();
        isGo = true;
    }

    /**
     *@param color1 圆圈颜色1
     *@param color2 圆圈颜色2
     *@param color3 圆圈颜色3
     * @param times 滚动时间 ms，负数表示使用默认时间*/
    public LoadingView(Context context, int color1, int color2, int color3, int times) {
        super(context);
        thread = new ThreadChangeColor();
        thread.start();
        isGo = true;

        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        if(times > 0){
            this.times = times;
        }
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**设置wrap_content*/
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(100,30);
            width = 100;
            height = 30;
        }else if(widthSpecMode == MeasureSpec.AT_MOST ){
            setMeasuredDimension(100,heightSpecSize);
            width = 100;
            height = heightSpecSize;
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,30);
            width = widthSpecSize;
            height = 30;
        }else{
            width = widthSpecSize;
            height = heightSpecSize;
        }

    }

    public void onDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);

        /**计算最大半径*/
        int radius = radius1;
        if(radius2 < radius3){
            if(radius < radius3){
                radius = radius3;
            }
        }else if(radius < radius2){
            radius = radius2;
        }
        /**横排绘制三个圆圈*/
        paint.setColor(color1);
        canvas.drawCircle(width / 2 - radius*3, height / 2, radius1, paint);

        paint.setColor(color2);
        canvas.drawCircle(width / 2, height / 2, radius2, paint);

        paint.setColor(color3);
        canvas.drawCircle(width / 2 + radius * 3, height / 2, radius3, paint);

        if(thread == null){
            thread = new ThreadChangeColor();
            thread.start();
        }
    }

    /**关闭*/
    public static void close(){
        isGo = false;
    }
    public class ThreadChangeColor extends Thread{
        public void run(){
            while (isGo){
                try {
                    Thread.sleep(times);
                    int color = color1;
                    color1=color2;
                    color2=color3;
                    color3=color;

                    int radius = radius1;
                    radius1 = radius2;
                    radius2 = radius3;
                    radius3 = radius;

                    postInvalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


