package com.dfst.media;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author lilinlin@supcon.com
 *         date 2016/04/29
 *         实现dialog，添加加载view
 */
public class AZLoading {

    protected AZLoading(){
    }

    public static class LoadingDot{
        private Dialog dialogDot;
        private boolean isOutsideCancel;
        private boolean isBackCancel;
        Context mcontext ;

        public LoadingDot(Context context){
            this.mcontext = context;
            this.isOutsideCancel = false;
            this.isBackCancel = false;
            configDot();

        }

        /**
         * @param context
         * @param isOutsideCancel 点击外部空白是否取消，true 取消，false 不取消
         * @param isBackCancel 点击返回键是否取消，true 取消，false 不取消
         */
        public LoadingDot(Context context, boolean isOutsideCancel, boolean isBackCancel){
            this.mcontext = context;
            this.isOutsideCancel = isOutsideCancel;
            this.isBackCancel = isBackCancel;
            configDot();
        }

        /**
         * @param context
         * @param isOutsideCancel 点击外部空白是否取消，true 取消，false 不取消
         * @param isBackCancel 点击返回键是否取消，true 取消，false 不取消
         * @param onCancelListener 点击外部和返回键的监听事件
         */
        public LoadingDot(Context context, boolean isOutsideCancel, boolean isBackCancel,
                                OnCancelListener onCancelListener){
            this.mcontext = context;
            this.isOutsideCancel = isOutsideCancel;
            this.isBackCancel = isBackCancel;
            configDot();
            dialogDot.setOnCancelListener(onCancelListener);
        }

        /**
         * 开启加载框
         */
        public void show() {
            dialogDot.setContentView(new LoadingView(mcontext));
            dialogDot.show();
        }

        /**
         * 开启加载框
         *
         * @param color1 圆圈颜色1
         * @param color2 圆圈颜色2
         * @param color3 圆圈颜色3
         * @param times  滚动时间 ms，负数表示使用默认时间
         */
        public void show( int color1, int color2, int color3, int times ) {
            dialogDot.setContentView(new LoadingView(mcontext, color1, color2, color3, times));
            dialogDot.show();
        }

        /**
         * 关闭加载框
         */
        public void dismiss() {
            if(null != dialogDot) {
                LoadingView.close();
                dialogDot.dismiss();
                dialogDot = null;
            }
        }

        private void configDot(){
            if (null == dialogDot) {
                dialogDot = new Dialog(mcontext, R.style.Dialog);
                /**触摸不返回*/
                if(!isOutsideCancel) {
                    dialogDot.setCanceledOnTouchOutside(false);
                }
                /**返回不退出*/
                if (!isBackCancel) {
                    dialogDot.setOnKeyListener(onKeyListener);
                }
            }
        }
        private Dialog.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    return true;
                }
                return false;
            }
        };
    }

    public static class LoadingWheel{
        private Dialog dialog;
        /**Dilaog内loading动画*/
        private AnimationDrawable animationDrawable;
        Context mcontext ;
        private boolean isOutsideCancel;
        private boolean isBackCancel;

        public LoadingWheel(Context context){
            this.mcontext = context;
            this.isOutsideCancel = false;
            this.isBackCancel = false;
            config();
        }

        /**
         * @param context
         * @param isOutsideCancel 点击外部空白是否取消，true 取消，false 不取消
         * @param isBackCancel 点击返回键是否取消，true 取消，false 不取消
         */
        public LoadingWheel(Context context,boolean isOutsideCancel,boolean isBackCancel){
            this.mcontext = context;
            this.isOutsideCancel = isOutsideCancel;
            this.isBackCancel = isBackCancel;
            config();
        }

        /**
         * @param context
         * @param isOutsideCancel 点击外部空白是否取消，true 取消，false 不取消
         * @param isBackCancel 点击返回键是否取消，true 取消，false 不取消
         * @param onCancelListener 点击外部和返回键的监听事件
         */
        public LoadingWheel(Context context,boolean isOutsideCancel,boolean isBackCancel,
                                    OnCancelListener onCancelListener){
            this.mcontext = context;
            this.isOutsideCancel = isOutsideCancel;
            this.isBackCancel = isBackCancel;
            config();
            dialog.setOnCancelListener(onCancelListener);
        }
        /**
         * 加载滚动加载框,默认大小
         */
        public  void show( ) {
            LinearLayout linearLayout = getLoadingLinearLayout( 100, null);
            dialog.setContentView(linearLayout);
            dialog.show();
        }
        /**
         * 加载滚动加载框
         * @param size 加载框大小
         */
        public  void show( int size ) {
            LinearLayout linearLayout = getLoadingLinearLayout( size, null);
            dialog.setContentView(linearLayout);
            dialog.show();
        }

        /**
         * 加载滚动加载框
         */
        public  void showWithText( ) {
            LinearLayout linearLayout = getLoadingLinearLayout( 100, "加载中...");
            dialog.setContentView(linearLayout);
            dialog.show();
        }

        /**
         * 加载滚动加载框
         * * @param size 加载框大小
         */
        public  void showWithText(int size) {
            LinearLayout linearLayout = getLoadingLinearLayout( size, "加载中...");
            dialog.setContentView(linearLayout);
            dialog.show();
        }

        /**
         * 加载滚动加载框
         *
         * @param text 提示信息
         */
        public  void showWithText( String text ) {
            LinearLayout linearLayout = getLoadingLinearLayout( 100, text);
            dialog.setContentView(linearLayout);
            dialog.show();
        }

        /**
         * 加载滚动加载框
         *
         * @param size 加载框大小
         * @param text 提示信息
         */
        public  void showWithText(  int size, String text) {
            LinearLayout linearLayout = getLoadingLinearLayout( size, text);
            dialog.setContentView(linearLayout);
            dialog.show();
        }

        /**
         * 关闭加载框
         */
        public void dismiss() {
            if (dialog != null) {
                animationDrawable.stop();
                dialog.dismiss();
                dialog = null;
            }
        }

        /**
         * 配置滚动加载框
         */
        private  void config( ) {
            if (null == dialog) {
                dialog = new Dialog(mcontext, R.style.Dialog);
                if(!isOutsideCancel) {
                    dialog.setCanceledOnTouchOutside(false);
                }
                if (!isBackCancel) {
                    dialog.setOnKeyListener(onKeyListener);
                }
            }
        }
        private Dialog.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    return true;
                }
                return false;
            }
        };

        /**
         * 绘制帧动画
         *
         * @param size    loading框大小
         * @return
         */
        private  LinearLayout getLoadingLinearLayout(  int size, String text) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            ImageView imageView = new ImageView(mcontext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            imageView.setImageResource(R.drawable.load_animation);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();

            LinearLayout linearLayout = new LinearLayout(mcontext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundResource(R.drawable.azpt_progress_backgroud);
            linearLayout.setLayoutParams(params);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setPadding(50, 50, 50, 50);
            linearLayout.addView(imageView);

            if (null != text) {
                TextView textView = new TextView(mcontext);
                textView.setText(text);
                textView.setPadding(0, 10, 0, 0);
                linearLayout.addView(textView, params1);
            }

            return linearLayout;
        }

        /**
         * loading帧动画
         */
        public AnimationDrawable getAnimationImageView( ) {
            ImageView imageView = new ImageView(mcontext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.load_animation);
            AnimationDrawable animationDrawableImage = (AnimationDrawable) imageView.getDrawable();
            return animationDrawableImage;
        }
    }
}
