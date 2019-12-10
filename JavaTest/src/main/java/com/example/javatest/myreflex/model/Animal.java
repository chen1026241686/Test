package com.example.javatest.myreflex.model;

/**
 * @author ChenYasheng
 * @date 2019/9/5
 * @Description
 */
public class Animal extends Object {

    public void eat_father() {
        System.out.println("父类公有方法--->吃饭");
    }

    public void sleep_father() {
        System.out.println("父类公有方法--->睡觉");
    }

    private void run_father() {
        System.out.println("父类私有方法--->跑");
    }

    public static void static_father() {
        System.out.println("父类静态公有静态方法");
    }

    private static void static_father_private() {
        System.out.println("父类静态私有静态方法");
    }


}
