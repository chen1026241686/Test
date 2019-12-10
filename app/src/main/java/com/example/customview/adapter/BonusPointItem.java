package com.example.customview.adapter;

/**
 * 种子收支明细
 */
public class BonusPointItem {

    /**
     * 种子明细id
     */
    private String id;
    /**
     * 种子获取时间
     */
    private String createTime;
    /**
     * 种子收支明细
     */
    private String pointsPaymentsDetail;
    /**
     * 种子说明
     */
    private String pointsExplain;

    /**
     * 备注
     */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPointsPaymentsDetail() {
        return pointsPaymentsDetail;
    }

    public void setPointsPaymentsDetail(String pointsPaymentsDetail) {
        this.pointsPaymentsDetail = pointsPaymentsDetail;
    }

    public String getPointsExplain() {
        return pointsExplain;
    }

    public void setPointsExplain(String pointsExplain) {
        this.pointsExplain = pointsExplain;
    }
}
