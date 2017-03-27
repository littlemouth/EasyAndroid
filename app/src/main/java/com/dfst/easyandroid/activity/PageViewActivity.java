package com.dfst.easyandroid.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.fragment.ContactsFragment;
import com.dfst.easyandroid.fragment.MainFragment;
import com.dfst.easyandroid.fragment.SelfFragment;
import com.dfst.easyandroid.fragment.WorldFragment;
import com.dfst.ui.widget.PageView;

/**
 * Created by yanfei on 2016-10-26.
 */
public class PageViewActivity extends FragmentActivity {

    private PageView tabView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageview);

        tabView = (PageView) findViewById(R.id.pageview_activity_pageview);
        int[] checkedIcons = {R.mipmap.home_checked, R.mipmap.recents_checked,
                R.mipmap.keypad_checked, R.mipmap.self_checked};
        int[] unCheckedIcons = {R.mipmap.home_unchecked, R.mipmap.recents_unchecked,
                R.mipmap.keypad_unchecked, R.mipmap.self_unchecked};
        String[] labels = {"主页", "联系人", "圈子", "我的"};
        PageView.Options options = new PageView.Options();
        options.pages = new Fragment[]{new MainFragment(), new ContactsFragment(),
                new WorldFragment(), new SelfFragment()};
        options.checkedIcons = checkedIcons;
        options.unCheckedIcons = unCheckedIcons;
        options.labels = labels;
        options.checkedLabelColor = ContextCompat.getColor(this, R.color.common_bg_green);
        options.unCheckedLabelColor = Color.GRAY;
        options.unCheckedItemBackgroundColor = Color.LTGRAY;
        options.checkedItemBackgroundColor = Color.BLUE;
        options.defaultPosition = 0;
        //options.labelTextSize = 15;
        tabView.init(options);
        //tabView.setTitleLayout(R.layout.test);
        tabView.setTabDeviderHeight(0.5f);
        //tabView.setTabHeight(DensityUtil.dip2px(this, 100));

    }

}
