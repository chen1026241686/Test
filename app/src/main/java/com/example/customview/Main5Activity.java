package com.example.customview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.customview.adapter.BonusPointDetailAdapter;
import com.example.customview.adapter.BonusPointItem;
import com.example.customview.adapter.HotExchange;
import com.example.customview.adapter.HotExchangeAdapter;
import com.example.customview.viewgroup.MyScrollView;

import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends AppCompatActivity {
    /**
     * 热门兑换商品
     */
    private RecyclerView hotExchangeList;
    private HotExchangeAdapter hotAdapter;
    private RecyclerView xRecyclerViewDetail;
    private BonusPointDetailAdapter bounsAdapter;
    private MyScrollView bonus_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        init();

        List a=new ArrayList();
        a.listIterator();
    }

    private void init() {
        hotExchangeList = findViewById(R.id.hotExchange);
        hotExchangeList.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(hotExchangeList.getContext(), GridLayoutManager.HORIZONTAL);
        dividerItemDecoration.setDrawable(getDrawable());
        hotExchangeList.addItemDecoration(dividerItemDecoration);

        hotAdapter = new HotExchangeAdapter(this, R.layout.item_hot_exchange);
        hotExchangeList.setAdapter(hotAdapter);

        List<HotExchange.Hot> data = new ArrayList<>();

        HotExchange.Hot h1 = new HotExchange.Hot();
        h1.setGoodsName("111111111111111");
        h1.setId("111111111");
        h1.setPrice(11111111);

        HotExchange.Hot h2 = new HotExchange.Hot();
        h2.setGoodsName("22222222222");
        h2.setId("222222222");
        h2.setPrice(2222222);

        HotExchange.Hot h3 = new HotExchange.Hot();
        h3.setGoodsName("33333333333");
        h3.setId("3333333333");
        h3.setPrice(3333333);

        data.add(h1);
        data.add(h2);
        data.add(h3);

        hotAdapter.addData(data);


        List<BonusPointItem> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BonusPointItem item = new BonusPointItem();
            item.setCreateTime("2019=48-78");
            item.setId(i + "");
            item.setPointsExplain("3333333333333333333333");
            item.setPointsPaymentsDetail("789");
            item.setRemark("hehehehehehheehhehehehe");
            items.add(item);
        }

        xRecyclerViewDetail = findViewById(R.id.rv_bonus_point_detail);

        bounsAdapter = new BonusPointDetailAdapter(this, R.layout.item_bouns_point);
        xRecyclerViewDetail.setAdapter(bounsAdapter);
        xRecyclerViewDetail.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerViewDetail.setHasFixedSize(true);
        xRecyclerViewDetail.setNestedScrollingEnabled(false);
        bounsAdapter.addData(items);


        bonus_content = findViewById(R.id.bonus_content);

        bonus_content.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {

                View view = scrollView.getChildAt(0);
                int height = view.getMeasuredHeight();
                height -= (scrollView.getMeasuredHeight() + scrollView.getScrollY());
                if (height <= 10) {
                    //height的大小可以自己控制，在到0时是临界点，这时候可以选择跳转或是加载下一页，也可以将height的高度设定的大一些来实现提前加载下一页的效果
                    ToastUtils.showLong("滑动到底部");


                    List<BonusPointItem> items = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        BonusPointItem item = new BonusPointItem();
                        item.setCreateTime("2019=48-78");
                        item.setId(i + "");
                        item.setPointsExplain("3333333333333333333333");
                        item.setPointsPaymentsDetail("789");
                        item.setRemark("hehehehehehheehhehehehe");
                        items.add(item);
                    }

                    bounsAdapter.addData(items);

                }
            }
        });


    }

    /**
     * 返回高度宽度为1的drawable
     *
     * @return
     */
    private Drawable getDrawable() {
        return new Drawable() {
            Paint paint = new Paint();

            {

                paint.setAntiAlias(true);
                paint.setColor(Color.parseColor("#ffececec"));
                paint.setStyle(Paint.Style.FILL);
            }

            @Override
            public void draw(@NonNull Canvas canvas) {
                canvas.drawRect(getBounds().left, getBounds().top, getBounds().right, getBounds().bottom, paint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.OPAQUE;
            }

            @Override
            public int getIntrinsicWidth() {
                return 1;
            }

            @Override
            public int getIntrinsicHeight() {
                return 1;
            }
        };
    }
}
