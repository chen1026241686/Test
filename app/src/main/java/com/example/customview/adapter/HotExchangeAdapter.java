package com.example.customview.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.example.customview.R;

/**
 * @author ChenYasheng
 * @date 2019/9/26
 * @Description 热门兑换商品适配器
 */
public class HotExchangeAdapter extends ListAdapter<HotExchange.Hot, CustomViewHolder> {
    public HotExchangeAdapter(Context context, int itemLayout) {
        super(context, itemLayout);
    }

    @Override
    protected void onBindViewHolder(CustomViewHolder holder, HotExchange.Hot item) {
        if (item != null) {
            //如果是敬请期待()
            if (TextUtils.isEmpty(item.getId())) {
                holder.setGone(R.id.tv_goods_price, false);
                holder.setGone(R.id.tv_goods_name, false);
                holder.setGone(R.id.tv_unit, false);
                holder.setImageResource(R.id.iv_goods_image, R.drawable.expect);
                return;
            } else {
                holder.setGone(R.id.tv_goods_price, true);
                holder.setGone(R.id.tv_goods_name, true);
                holder.setGone(R.id.tv_unit, true);
            }
            holder.setText(R.id.tv_goods_price, String.format("%.0f", item.getPrice()));
            holder.setText(R.id.tv_goods_name, item.getGoodsName() == null ? "" : item.getGoodsName());
            holder.setImageResource(R.id.iv_goods_image, R.drawable.expect);
        }
    }
}
