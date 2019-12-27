package com.example.customview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.customview.adapter.BonusPointDetailAdapter;
import com.example.customview.adapter.BonusPointItem;
import com.example.customview.viewgroup.MyScrollView2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import dalvik.system.PathClassLoader;

public class MainActivity3 extends BaseActivity {


    private LinearLayout father;

    private RecyclerView mRecyclerView;

    private BonusPointDetailAdapter mBonusPointDetailAdapter;

    private MyScrollView2 scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //提交一次


        setContentView(R.layout.activity_main3);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mBonusPointDetailAdapter = new BonusPointDetailAdapter(this, R.layout.item_bouns_point);


        List<BonusPointItem> items = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            BonusPointItem item = new BonusPointItem();
            item.setCreateTime("2019=48-78");
            item.setId((i + 1) + "");
            item.setPointsExplain(i + "AAAAAAAAAAAAAAAAAA");
            item.setPointsPaymentsDetail("" + (i + 1));
            item.setRemark("hehehehehehheehhehehehe");
            items.add(item);
        }
        mBonusPointDetailAdapter.addData(items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mBonusPointDetailAdapter);

        scroll = findViewById(R.id.scroll);


        ClassLoader classLoader = getClassLoader();
        while (classLoader != null) {
            Log.e("AAA", classLoader.getClass().getName());
            classLoader = classLoader.getParent();
        }


        Log.e("AAA", "-------------------------------------------------------------------");
        Log.e("AAA", Activity.class.getClassLoader().getClass().getName());
        Log.e("AAA", AppCompatActivity.class.getClassLoader().getClass().getName());

        PathClassLoader pathClassLoader;


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View view = scroll.getChildAt(0);
            Log.e("FFF", "view.getLeft-->" + view.getLeft());
            Log.e("FFF", "view.getTop-->" + view.getTop());
            Log.e("FFF", "view.getRight-->" + view.getRight());
            Log.e("FFF", "view.getBottom-->" + view.getBottom());

            Log.e("FFF", "view.getHeight--->" + view.getHeight());


        }
    }

    public static void printObject(Object obj) {

        Collection conllections = new HashSet();
        ((HashSet) conllections).add("1");
        ((HashSet) conllections).add("2");
        ((HashSet) conllections).add("3");
        ((HashSet) conllections).add("1");

        ((HashSet) conllections).contains("1");

        System.out.println("HashSet-->" + conllections.size());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void ac3(View view) {
        view.scrollBy(-2, 0);
        Log.e("FFF", "(1 << 1)=" + (1 << 1));

    }

    int i = 0;

    public void add(View view) {

        TextView textView = new TextView(this);
        textView.setText(i + "");
        i++;
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        father.addView(textView);

        for (int i = 0; i < father.getChildCount(); i++) {
            TextView v = (TextView) father.getChildAt(i);
            Log.e("FFF", "添加之后遍历---->index---->" + i + "  内容----->" + v.getText());
        }

    }

    public void delete(View view) {
        if (father.getChildCount() > 0) {


            int position = father.getChildCount() / 2;

            father.removeViewAt(position);

            Log.e("FFF", "删除位置为----->" + position);

            for (int i = 0; i < father.getChildCount(); i++) {
                TextView v = (TextView) father.getChildAt(i);
                Log.e("FFF", "删除之后遍历---->index---->" + i + "  内容----->" + v.getText());
            }

        } else {
            ToastUtils.showLong("没有可以删除的了");
        }
    }

}
