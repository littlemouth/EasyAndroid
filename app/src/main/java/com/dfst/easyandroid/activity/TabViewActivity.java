package com.dfst.easyandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.fragment.ContactsFragment;
import com.dfst.easyandroid.fragment.MainFragment;
import com.dfst.easyandroid.fragment.WorldFragment;
import com.dfst.ui.widget.TabView;

/**
 * Created by littlemouth on 16-11-26.
 */
public class TabViewActivity extends FragmentActivity {
    private TabView tabView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabview);

        tabView = (TabView) findViewById(R.id.tabview_activity_tabview);
        TabView.Options options = new TabView.Options();
        options.tabs = new Fragment[] {new MainFragment(), new ContactsFragment(),
                new WorldFragment()};
        options.labels = new String[] {"西甲", "英超", "冠军杯"};
        options.labelsTextSize = 16;
        tabView.build(options);
    }
}
