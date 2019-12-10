package com.example.customview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.example.customview.R;


public class BonusPointDetailAdapter extends ListAdapter<BonusPointItem, CustomViewHolder> {
    public BonusPointDetailAdapter(Context context, int itemLayout) {
        super(context, itemLayout);
    }

    @Override
    protected void onBindViewHolder(CustomViewHolder holder, BonusPointItem item) {
        //种子收支
        String point = item.getPointsPaymentsDetail();
        //不空
        if (!TextUtils.isEmpty(point)) {
            try {
                //种子正负的时候显示的颜色不同
                int num = Integer.parseInt(point);
                int color = num >= 0 ? Color.parseColor("#df2d1f") : Color.parseColor("#333333");
                holder.setTextColor(R.id.point, color);
                //大于0需要加正号
                if (num > 0) {
                    point = "+" + num;
                }
            } catch (Exception e) {
                holder.setTextColor(R.id.point, Color.parseColor("#df2d1f"));
            }
        } else {
            point = "";
        }

        String remark = item.getRemark();
        if (!TextUtils.isEmpty(remark)) {
            holder.setVisible(R.id.remarks, true);
            holder.setText(R.id.remarks, remark);
        } else {
            holder.setGone(R.id.remarks, false);
        }


        holder.setText(R.id.point, point);

        //种子说明
        holder.setText(R.id.point_name, item.getPointsExplain());
        //种子获取时间
        holder.setText(R.id.point_time, item.getCreateTime());
    }

}
