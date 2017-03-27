package com.dfst.easyandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dfst.easyandroid.R;
import com.dfst.ui.adapter.SimpleGroupListAdapter;
import com.dfst.ui.widget.SimpleGroupListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanfei on 2016-10-31.
 */
public class SimpleGroupListActivity extends Activity {
    private SimpleGroupListView listView;
    private SimpleGroupListAdapter adapter;
    private List<SimpleGroupListAdapter.Item> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_group_list);

        listView = (SimpleGroupListView) findViewById(R.id.listView);
        adapter = new SimpleGroupListAdapter(this, createData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SimpleGroupListActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<SimpleGroupListAdapter.Item> createData() {
        String nameStr = "李霞 ,杜重治,陈锋,郑伯宁,施华军,吴书振,张宁,马世波,张章,张竹影,韩庆福,刘勇,张忆湫,尚志兴,"
                + "杜若芳,杨乔松,闫跃进,孙凯,赖祥校,郭晖,贺光明,D邓小燕,白莉惠,杨海霞,利旭日,范永胜,于怀斌,赵淑娜,"
                + "张淑杰,陈俊军,郭增杰,林云,郭述龙,杨军,张海龙,耿静,程水平,AFAA CHINA,"
                + "Power Music,范闳乔,杨波,李欣航,李广文,骆斯璐,D杜健,丁祥国,"
                + "谢蕤,晁农平,张久一,郭安翔,郭红,陈海伟,施姝,2196365,罗华明,伍前辉,王玫,袁翔,郑剑文,"
                + "李★,吕娟,毛xx,周x,杨秀琴,张锋,王茜,侯刚,单彦名,丁一虹,邓晓莹,黄筱薇,丁海霞"
                + "李霞 ,杜重治,陈锋,郑伯宁,施华军,吴书振,张宁,马世波,张章,张竹影,韩庆福,刘勇,张忆湫,尚志兴,"
                + "杜若芳,杨乔松,闫跃进,孙凯,赖祥校,郭晖,贺光明,D邓小燕,白莉惠,杨海霞,利旭日,范永胜,于怀斌,赵淑娜,"
                + "张淑杰,陈俊军,郭增杰,林云,郭述龙,杨军,张海龙,耿静,程水平,AFAA CHINA,"
                + "Power Music,范闳乔,杨波,李欣航,李广文,骆斯璐,D杜健,丁祥国,"
                + "谢蕤,晁农平,张久一,郭安翔,郭红,陈海伟,施姝,2196365,罗华明,伍前辉,王玫,袁翔,郑剑文";

        String[] names = nameStr.split(",");
        list = new ArrayList<>(600);
        SimpleGroupListAdapter.Item item = null;
        for (String name : names) {
            item = new SimpleGroupListAdapter.Item(name);
            list.add(item);
        }

        SimpleGroupListAdapter.Item top1 = new SimpleGroupListAdapter.Item("新建联系人");
        top1.type = SimpleGroupListAdapter.ItemType.TOP;
        list.add(top1);

        SimpleGroupListAdapter.Item star1 = new SimpleGroupListAdapter.Item("燕飞");
        star1.type = SimpleGroupListAdapter.ItemType.STAR;
        list.add(star1);

        SimpleGroupListAdapter.Item top2 = new SimpleGroupListAdapter.Item("群聊");
        top2.type = SimpleGroupListAdapter.ItemType.TOP;
        list.add(top2);

        SimpleGroupListAdapter.Item star2 = new SimpleGroupListAdapter.Item("雯雯");
        star2.type = SimpleGroupListAdapter.ItemType.STAR;
        list.add(star2);

        return list;
    }
}
