package com.dfst.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfst.core.util.DensityUtil;
import com.dfst.ui.R;

/**
 * Created by yanfei on 2016-10-27.
 */
public class PageItem extends FrameLayout{
    private LinearLayout checkedLayout, unCheckedLayout;
    private ImageView checkedIcon, unCheckedIcon;
    private TextView labels, unCheckedLabels;

    private boolean checked;

    private Context mContext;

    public PageItem(Context context) {
        super(context);
        mContext = context;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        setLayoutParams(params);

        /** checked layout **/
        FrameLayout.LayoutParams checkedParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        checkedLayout = new LinearLayout(context);
        checkedLayout.setLayoutParams(checkedParams);
        checkedLayout.setOrientation(LinearLayout.VERTICAL);
        checkedLayout.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        imageParams.weight = 5;
        imageParams.topMargin = DensityUtil.dip2px(context, 3);
        checkedIcon = new ImageView(context);
        checkedIcon.setLayoutParams(imageParams);
        checkedIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        checkedLayout.addView(checkedIcon);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        textParams.weight = 3;
        textParams.bottomMargin = DensityUtil.dip2px(context, 2);
        labels = new TextView(context);
        labels.setLayoutParams(textParams);
        labels.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        labels.setTextSize(11);
        checkedLayout.addView(labels);
        checkedLayout.setAlpha(0f);

        addView(checkedLayout);

        /** unchecked layout **/
        FrameLayout.LayoutParams unCheckedParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        unCheckedLayout = new LinearLayout(context);
        unCheckedLayout.setLayoutParams(unCheckedParams);
        unCheckedLayout.setOrientation(LinearLayout.VERTICAL);
        unCheckedLayout.setGravity(Gravity.CENTER);


        LinearLayout.LayoutParams unImageParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        unImageParams.weight = 5;
        unImageParams.topMargin = DensityUtil.dip2px(context, 3);
        unCheckedIcon = new ImageView(context);
        unCheckedIcon.setLayoutParams(unImageParams);
        unCheckedIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        unCheckedIcon.setImageResource(R.mipmap.home_checked);
        unCheckedLayout.addView(unCheckedIcon);

        LinearLayout.LayoutParams unTextParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        unTextParams.weight = 3;
        unTextParams.bottomMargin = DensityUtil.dip2px(context, 2);
        unCheckedLabels = new TextView(context);
        unCheckedLabels.setLayoutParams(unTextParams);
        unCheckedLabels.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        unCheckedLabels.setTextSize(11);
        unCheckedLabels.setText("主页");
        unCheckedLayout.addView(unCheckedLabels);

        addView(unCheckedLayout);
    }

    public void setItemBackGroundColor(int checkedColor, int unCheckedColor) {
        try {
            checkedLayout.setBackgroundColor(ContextCompat.getColor(mContext, checkedColor));
        } catch (RuntimeException e) {
            checkedLayout.setBackgroundColor(checkedColor);
        }
        try {
            unCheckedLayout.setBackgroundColor(ContextCompat.getColor(mContext, unCheckedColor));
        } catch (RuntimeException e) {
            unCheckedLayout.setBackgroundColor(unCheckedColor);
        }
    }

    public void setImageResource(int checkedResId, int unCheckedResId) {
        checkedIcon.setImageResource(checkedResId);
        unCheckedIcon.setImageResource(unCheckedResId);
            /*int w = View.MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            checkedIcon.measure(w, h);
            int width = checkedIcon.getMeasuredWidth();

            FrameLayout.LayoutParams promptParams = new FrameLayout.LayoutParams(promptWidth, promptWidth);
            promptParams.leftMargin = (int) (width * 0.6f);
            promptParams.topMargin = DensityUtil.dip2px(itemContext, 3);
            promptParams.gravity = Gravity.CENTER_HORIZONTAL;
            prompt.setLayoutParams(promptParams);*/
    }

    public void setLabelText(String checkedText, String unCheckedText) {
        labels.setText(checkedText);
        unCheckedLabels.setText(unCheckedText);
    }

    public void setLabelTextColor(int checkedColor, int unCheckedColor) {
        try {
            labels.setTextColor(ContextCompat.getColor(mContext, checkedColor));
        } catch (RuntimeException e) {
            labels.setTextColor(checkedColor);
        }
        try {
            unCheckedLabels.setTextColor(ContextCompat.getColor(mContext, unCheckedColor));
        } catch (RuntimeException e) {
            unCheckedLabels.setTextColor(unCheckedColor);
        }
    }

    public void setAlpha(float checkedAlpha, float unCheckAlpha) {
        checkedLayout.setAlpha(checkedAlpha);
        unCheckedLayout.setAlpha(unCheckAlpha);
    }

    public void setLabelVisibility(int visibly) {
        labels.setVisibility(visibly);
        unCheckedLabels.setVisibility(visibly);
    }

    public View getCheckedView() {
        return checkedLayout;
    }

    public View getUnCheckedView() {
        return unCheckedLayout;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            //checkedLayout.setVisibility(VISIBLE);
            //unCheckedLayout.setVisibility(GONE);
            setAlpha(1f, 0f);
        } else {
            //checkedLayout.setVisibility(GONE);
            //unCheckedLayout.setVisibility(VISIBLE);
            setAlpha(0f, 1f);
        }
    }

    public boolean isChecked() {
        return checked;
    }
}
