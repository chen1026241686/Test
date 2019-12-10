package com.example.customview.adapter;

/**
 * 账户种子
 */
public class MyBonusPoint {

    /**
     * id
     */
    private double id;
    /**
     * 可用种子
     */
    private double usablePoints;

    /**
     * 即将过期种子
     */
    private String pointsHints;

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getUsablePoints() {
        return usablePoints;
    }

    public void setUsablePoints(double usablePoints) {
        this.usablePoints = usablePoints;
    }

    public String getPointsHints() {
        return pointsHints;
    }

    public void setPointsHints(String pointsHints) {
        this.pointsHints = pointsHints;
    }
}
