package com.example.javatest.myreflex.model;


/**
 * @author ChenYasheng
 * @date 2019/9/5
 * @Description
 */
public class Cat extends Animal {


    static {


    }

    public Cat() {

    }

    public static void aa(){}

    private void cat_private() {
        System.out.println("猫--->私有方法");
    }

    public void catchMouse() {
        System.out.println("猫--->公有方法");
    }


    private class Cat_1 {
        public void cat_1() {
            System.out.println("猫--->内部私有类--->公有方法");
        }

        private void cat_2() {
            System.out.println("猫--->内部私有类--->私有方法");
        }
    }

    public class Cat_2 {
        public void cat_1() {
            System.out.println("猫--->内部公有类--->公有方法");
        }

        private void cat_2() {
            System.out.println("猫--->内部公有类--->私有方法");
        }
    }

    public static class Cat_3 {
        public void cat_1() {
            System.out.println("猫--->内部公有静态类--->公有方法");
        }

        private void cat_2() {
            System.out.println("猫--->内部公有静态类--->私有方法");
        }
    }

    private static class Cat_4 {
        public void cat_1() {
            System.out.println("猫--->内部私有静态类--->公有方法");
        }

        private void cat_2() {
            System.out.println("猫--->内部私有静态类--->私有方法");
        }
    }
}
