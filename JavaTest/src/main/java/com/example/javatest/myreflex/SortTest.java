package com.example.javatest.myreflex;

import java.security.SecureClassLoader;

/**
 * @author ChenYasheng
 * @date 2019/12/16
 * @Description 排序算法
 */
public class SortTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        testSort();


//        int a = 0x10;//16
//        int b = 0x20;//32
//        System.out.println("a+b=" + (a + b));


//        String s = "10";
//        String s1 = "20";
//        System.out.println("s=" + (Integer.toHexString(Integer.parseInt(s, 16))));
//        System.out.println("s1=" + (Integer.toHexString(Integer.parseInt(s1, 16))));
//
//        System.out.println("s+s1=" + (Integer.parseInt(s, 16) + Integer.parseInt(s1, 16)));


//        String s = "?";
//        System.out.println(Integer.toHexString(s.charAt(0)));

//        String a = "aaabbbccccbbaab";
//        System.out.println(a.replaceAll("b+", "x"));
//
//        System.out.println(MyClass.class.getName());


        ClassLoader1 classLoader1 = new ClassLoader1();
        ClassLoader2 classLoader2 = new ClassLoader2();

        Class clz1 = classLoader1.findClass("com.example.javatest.myreflex.SortTest$MyClass");
        Class clz2 = classLoader1.findClass("com.example.javatest.myreflex.SortTest$MyClass");
        Class clz3 = classLoader2.findClass("com.example.javatest.myreflex.SortTest$MyClass");

        Object ob1 = clz1.newInstance();
        Object ob2 = clz2.newInstance();
        Object ob3 = clz3.newInstance();

        System.out.println(ob1.getClass().getName());
        System.out.println(ob2.getClass().getName());
        System.out.println(ob3.getClass().getName());

        System.out.println(ob1.getClass().getClassLoader().getClass().getName());
        System.out.println(ob2.getClass().getClassLoader().getClass().getName());
        System.out.println(ob3.getClass().getClassLoader().getClass().getName());

        System.out.println(clz1 == clz2);
        System.out.println(clz1 == clz3);
    }

    static class MyClass {
        public MyClass() {

        }
    }

    static class ClassLoader1 extends ClassLoader {
        public ClassLoader1() {

        }

        @Override
        public Class<?> findClass(String s) throws ClassNotFoundException {
            return Class.forName(s);
        }
    }

    static class ClassLoader2 extends ClassLoader {
        public ClassLoader2() {

        }

        @Override
        public Class<?> findClass(String s) throws ClassNotFoundException {
            return Class.forName(s);
        }
    }


    private static void testSort() {
        int[] arrays = new int[]{1, 3, 7, 4, 10, 5, 12, 2, -1, -20};

        System.out.println("--------------排序之前--------------");
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i] + ",");
        }
        System.out.println("\n");


        quickSort(arrays, 0, arrays.length);


        System.out.println("--------------排序之后--------------");
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i] + ",");
        }

    }


    /**
     * 冒泡排序（相邻的两个排序，同一个数组）
     *
     * @param array
     */
    public static void bubbleSort(int array[]) {

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    array[j] = array[j] ^ array[j + 1];
                    array[j + 1] = array[j] ^ array[j + 1];
                    array[j] = array[j] ^ array[j + 1];
                }
            }
        }
    }

    /**
     * 选择排序（都和第一个比较，同一个数组）
     *
     * @param array
     */
    public static void selectSort(int array[]) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] > array[j]) {
                    array[i] = array[i] ^ array[j];
                    array[j] = array[i] ^ array[j];
                    array[i] = array[i] ^ array[j];
                }
            }
        }
    }

    /**
     * 插入排序（排队插叙，同一个数组）
     *
     * @param array
     */
    public static void insertSort(int array[]) {

        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j - 1] > array[j]) {
                    array[j - 1] = array[j - 1] ^ array[j];
                    array[j] = array[j - 1] ^ array[j];
                    array[j - 1] = array[j - 1] ^ array[j];
                }
            }
        }
    }

    /**
     * 快速排序（挖坑排序，哪个被挖出来再需要往里面填）
     *
     * @param a
     */
    public static void quickSort(int a[], int left, int right) {

        //归位
        //

    }

}
