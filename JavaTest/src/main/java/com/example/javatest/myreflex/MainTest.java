package com.example.javatest.myreflex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author ChenYasheng
 * @date 2019/11/11
 * @Description
 */
public class MainTest {

    //list
    {
        ArrayList<String> list1;
        LinkedList<String> list2;
    }

    //map
    {
        HashMap<String, String> map1;
        LinkedHashMap<String, Object> map2;
        Hashtable<String, String> map3;
    }


    //set
    {
        HashSet<String> set1;
        LinkedHashSet<String> set2;
        TreeSet<String> set3;
    }

    //queue
    {
        LinkedBlockingDeque<String> e;
        LinkedBlockingQueue<String> f;
        LinkedTransferQueue<String> g;
    }


    private static class Test {
        String name;
        String sex;
        int age;

        public Test(String name, String sex, int age) {
            this.name = name;
            this.sex = sex;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && o instanceof Test) {
                return ((Test) o).name.equals(this.name) && ((Test) o).age == this.age;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

    public static void main(String args[]) throws CloneNotSupportedException {
        int[] a = new int[]{2, 3, 4, 6, 6};
        List list = Arrays.asList(1, 2, "哈");

//        list.remove(0);
//        list.add(0);
        list.set(2,"hei");

        System.out.println(list.get(2));
    }

    static class Person {
        public String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "my name is " + name;
        }
    }

    static class CloneClass implements Cloneable {

        public String name;

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    /**
     * 冒泡排序
     */
    public static void bubbleSort() {
        int[] arraySort = new int[]{12, 23, 34, 42, 34, 2, 5, 6, 7, 1, 2, 4, 5, 2, 3, 34, 56, 75, 86, 83, 0};

        for (int i = 0; i < arraySort.length - 1; i++) {
            for (int j = 0; j < arraySort.length - 1 - i; j++) {
                if (arraySort[j] > arraySort[j + 1]) {
                    int num = arraySort[j];
                    arraySort[j] = arraySort[j + 1];
                    arraySort[j + 1] = num;
                }
            }
        }
        for (int i = 0; i < arraySort.length; i++) {
            System.out.println("冒泡排序----->" + arraySort[i]);
        }
    }

    /**
     * 选择排序
     */
    public static void selectSort() {
        int[] arraySort = new int[]{12, 23, 34, 42, 34, 2, 5, 6, 7, 1, 2, 4, 5, 2, 3, 34, 56, 75, 86, 83, 0};

        for (int i = 0; i < arraySort.length - 1; i++) {
            for (int j = i + 1; j < arraySort.length; j++) {
                if (arraySort[i] > arraySort[j]) {
                    int num = arraySort[i];
                    arraySort[i] = arraySort[j];
                    arraySort[j] = num;
                }
            }
        }

        for (int i = 0; i < arraySort.length; i++) {
            System.out.println("选择排序----->" + arraySort[i]);
        }
    }

    /**
     * 归并排序
     */
    public static void mergeSort() {


    }


}
