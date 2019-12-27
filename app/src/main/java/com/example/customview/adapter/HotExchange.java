package com.example.customview.adapter;

import androidx.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门兑换
 */
public class HotExchange {

    /**
     * 热门兑换商品list
     */
    private List<Hot> hotExchangeList = new ArrayList<>();

    /**
     * 是否有更多
     */
    private boolean isMore;

    public List<Hot> getHotList() {
        return hotExchangeList;
    }

    public void setHotList(List<Hot> hotList) {
        this.hotExchangeList = hotList;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }


    /**
     * 热门兑换商品
     */
    public static class Hot implements Comparable<Hot> {
        /**
         * 编号
         */
        private String id;

        /**
         * 商品名称
         */
        private String goodsName;

        /**
         * 商品单价
         */
        private double price ;
        /**
         * 推荐顺序
         */
        private String recommendSort = "0";
        /**
         * 图片地址
         */
        private String imageUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getRecommendSort() {
            return recommendSort;
        }

        public void setRecommendSort(String recommendSort) {
            this.recommendSort = recommendSort;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public int compareTo(@NonNull Hot o) {
            try {
                return Integer.parseInt(recommendSort) - Integer.parseInt(o.recommendSort);
            } catch (Exception e) {
                Log.e("FFF", "exception=" + e.toString());
            }
            return 0;
        }
    }


}
