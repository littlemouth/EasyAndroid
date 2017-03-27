package com.dfst.easyandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.adapter.MyGroupListAdapter;
import com.dfst.ui.widget.GroupListView;

/**
 * Created by yanfei on 2016-11-07.
 */
public class CommonGroupListActivity extends Activity {

    private GroupListView listView;
    private MyGroupListAdapter adapter;
    private List<Date> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_group_list);

        createData();
        listView = (GroupListView) findViewById(R.id.listView);
        adapter = new MyGroupListAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CommonGroupListActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                listView.setSelection(0);
            }
        });
    }

    private void createData() {
        list = new ArrayList<Date>();
        for (int i = 1; i < 31; i++) {
            Date date = new Date();
            date.date = "11月" + i + "日";
            List<Game> games = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                Game game = new Game();
                game.time =  i/5+ ":" + j/6;
                game.game = "皇家马德里  vs  巴萨罗那";
                games.add(game);
            }
            date.games = games;
            list.add(date);
        }
    }

    public static class Date {
        public String date;
        public List<Game> games;
    }

    public static class Game {
        public String time;
        public String game;
    }

}
