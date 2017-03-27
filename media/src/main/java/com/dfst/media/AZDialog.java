package com.dfst.media;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author lilinlin@supcon.com
 * date 2016/04/22
 * 该类用于动态加载alert_dialog.xml，生成dialog
 */
public class AZDialog extends Dialog{
    private float width;
    public AZDialog(Context context) {
        super(context);
    }

    public AZDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private int  buttonTextColor = Color.parseColor("#007aff");
        private int  titleColor = Color.BLACK;
        private int  contentColor = Color.DKGRAY;
        private float buttonTextSize = 16f;
        private float titleSize = 16f;
        private float contentSize = 16f;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private OnKeyListener onKeyListener = new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    return true;
                }else {
                    return false;
                }
            }
        };

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**设置标题
         * @param title 标题*/
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**设置view*/
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public void setbuttonTextColor(int buttonTextColor) {
            this.buttonTextColor = buttonTextColor;
        }

        public void setTitleColor(int titleColor) {
            this.titleColor = titleColor;
        }

        public void setContentColor(int contentColor) {
            this.contentColor = contentColor;
        }

        public void setbuttonTextSize(float buttonTextSize){
            this.buttonTextSize = buttonTextSize;
        }

        public void setTitleSize(float titleSize){
            this.titleSize =  titleSize;
        }

        public void setContentSize(float contentSize){
            this.contentSize =   contentSize;
        }

        /**positive按钮
         * @param positiveButtonText 按钮名称
         * @param listener 监听*/
        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

       /**positive按钮
        * @param negativeButtonText 按钮名称
        * @param listener 监听*/
        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**动态加载alert_dialog.xml 创建dialog
         * @param outsideBack 点击外部是否退出，true表示退出，false为不退出
         * @param clickBack 点击返回是否退出，true表示退出，false为不退出*/
        public AZDialog create(boolean outsideBack,boolean clickBack) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            final float dialogWidth = (float) (width*0.7);

            /**设置dialog主题*/
            final AZDialog dialog = new AZDialog(context, R.style.Dialog);
            if(!outsideBack) {
                dialog.setCanceledOnTouchOutside(false);
            }
            if(!clickBack) {
                dialog.setOnKeyListener(onKeyListener);
            }
            final LinearLayout azpt_linearLayout_dialog = new LinearLayout(context);
            azpt_linearLayout_dialog.setOrientation(LinearLayout.VERTICAL);
            azpt_linearLayout_dialog.setBackgroundResource(R.drawable.azpt_alert_backgroud);

            dialog.addContentView(azpt_linearLayout_dialog,
                            new ViewGroup.LayoutParams((int) dialogWidth,
                            ViewGroup.LayoutParams.WRAP_CONTENT));


            /**设置标题*/
            if(null != title){
                TextView titleTextView = new TextView(context);
                titleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                titleTextView.setPadding(30, 20, 30, 20);
                titleTextView.setText(title);
                titleTextView.setTextColor(titleColor);
//                titleTextView.setTextSize( DensityUtil.dip2px(context,titleSize));
                titleTextView.setTextSize(titleSize);

                azpt_linearLayout_dialog.addView(titleTextView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

            }

            if (contentView != null) {
                /**没有内容，设置view*/
                LinearLayout azpt_linearlayout_view = new LinearLayout(context);
                azpt_linearlayout_view.setPadding(30, 20, 30, 20);
                azpt_linearlayout_view.setGravity(Gravity.CENTER);
                azpt_linearlayout_view.removeAllViews();
                azpt_linearlayout_view.addView(contentView, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                azpt_linearLayout_dialog.addView(azpt_linearlayout_view);
            }

            /**设置内容*/
            if (message != null) {
                final TextView messageTextView = new TextView(context);
                messageTextView.setPadding(30, 20, 30, 20);
                messageTextView.setGravity(Gravity.CENTER);
                messageTextView.setText(message);
                messageTextView.setTextColor(contentColor);
                messageTextView.setTextSize( contentSize);

                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);

                /**字体的实际高度*/
                final int[] textViewHeight = {0};
                final ViewTreeObserver observer = messageTextView.getViewTreeObserver();
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        textViewHeight[0] = messageTextView.getMeasuredHeight();
                        /**如果字数较少，则选择默认高度*/
                        if(textViewHeight[0] < dialogWidth*0.3) {
                            messageTextView.setLayoutParams( new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT, (int) (dialogWidth*0.3)));
                        }
                        /**如果字数过多，则选择使用下拉条*/
                        else {
                            messageTextView.setVerticalScrollBarEnabled(true);
                            messageTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
                            messageTextView.setLayoutParams( new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT, (int) (dialogWidth * 0.5)));

                        }


                    }
                });

                azpt_linearLayout_dialog.addView(messageTextView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            LinearLayout azpt_linearLayout_btn = new LinearLayout(context);
            azpt_linearLayout_btn.setOrientation(LinearLayout.HORIZONTAL);
            /**按钮背景*/
            if(null != positiveButtonText && null != negativeButtonText){
                azpt_linearLayout_btn.setBackgroundResource(R.mipmap.azpt_alert_line);
            }else{
                TextView line = new TextView(context);
                line.setBackgroundResource(R.mipmap.azpt_alert_line_alone);
                azpt_linearLayout_dialog.addView(line, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 1));
            }

            /**设置两个按钮*/
            if(null != positiveButtonText && null != negativeButtonText){
                Button positiveBtn = new Button(context);
                positiveBtn.setBackgroundResource(R.drawable.azpt_alert_bt_pressed);
                positiveBtn.setTextColor(buttonTextColor);
                positiveBtn.setTextSize( buttonTextSize);
                positiveBtn.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }

                azpt_linearLayout_btn.addView(positiveBtn, new LinearLayout.LayoutParams(
                        (int) (dialogWidth * 0.5), ViewGroup.LayoutParams.MATCH_PARENT, 1));

                Button negativeBtn = new Button(context);
                negativeBtn.setBackgroundResource(R.drawable.azpt_alert_bt_pressed);
                negativeBtn.setTextColor(buttonTextColor);
                negativeBtn.setTextSize( buttonTextSize);
                negativeBtn.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }

                azpt_linearLayout_btn.addView(negativeBtn,new LinearLayout.LayoutParams(
                        (int) (dialogWidth*0.5), ViewGroup.LayoutParams.MATCH_PARENT,1));
            }
            /**设置positivebutton*/
            else if (positiveButtonText != null) {
                Button positiveBtn = new Button(context);
                positiveBtn.setBackgroundResource(R.drawable.azpt_alert_bt_pressed);
                positiveBtn.setTextColor(buttonTextColor);
                positiveBtn.setTextSize(buttonTextSize);
                positiveBtn.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }

                azpt_linearLayout_btn.addView(positiveBtn, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            }
            /**设置negativebutton*/
            else if (negativeButtonText != null) {
                Button negativeBtn = new Button(context);
                negativeBtn.setBackgroundResource(R.drawable.azpt_alert_bt_pressed);
                negativeBtn.setTextColor(buttonTextColor);
                negativeBtn.setTextSize(buttonTextSize);
                negativeBtn.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }

                azpt_linearLayout_btn.addView(negativeBtn, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            }
            azpt_linearLayout_dialog.addView(azpt_linearLayout_btn);

            return dialog;
        }
    }
}
