package com.dfst.easyandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.adapter.FallViewAdapter;
import com.dfst.ui.widget.FallView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yanfei on 2017-03-06.
 */
public class FallViewActivity extends Activity {
    private FallView fallView;
    private FallViewAdapter adapter;
    private Button refreshBtn;
    private Random random = new Random();

    private List<FallItem> data = new ArrayList<>();
    private int[] images = {R.mipmap.image1, R.mipmap.image2, R.mipmap.image3, R.mipmap.image4, R.mipmap.image5};
    private String[] descriptions = {"迷雾", "郁金香（学名：Tulipa gesneriana），百合科郁金香属的草本植物，是土耳其、哈萨克斯坦、荷兰的国花。",
            "沙漠，主要是指地面完全被沙所覆盖、植物非常稀少、雨水稀少、空气干燥的荒芜地区。",
            "这是什么花？",
            "灯塔是建于航道关键部位附近的一种塔状发光航标。灯塔是一种固定的航标，用以引导船舶航行或指示危险区。现代大型灯塔结构体内有良好的生活、通信设施，可供管理人员居住，但也有重要的灯塔无人看守。"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fallview);
        refreshBtn = (Button) findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 5; i++) {
                    int index = random.nextInt(5);
                    FallItem item = new FallItem(images[index], descriptions[index]);
                    data.add(item);
                }
                adapter.notifyDataSetChanged();
            }
        });
        fallView = (FallView) findViewById(R.id.fallView);
        createData();
        adapter = new FallViewAdapter(this, data);
        fallView.setAdapter(adapter);
    }

    private void createData() {
        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(5);
            FallItem item = new FallItem(images[index], descriptions[index]);
            data.add(item);
        }
    }

    public static class FallItem {
        public String description;
        public int resId;

        public FallItem(int resId, String description) {
            this.resId = resId;
            this.description = description;
        }

    }
}
