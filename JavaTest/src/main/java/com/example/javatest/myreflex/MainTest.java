package com.example.javatest.myreflex;

import java.io.BufferedReader;
import java.io.FileReader;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

import sun.util.resources.cldr.ka.LocaleNames_ka;

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


    static class Link<T> {

        private Node<T> head, tail;

        static class Node<T> {
            public T t;
            public Node next;

            public Node(T t) {
                this.t = t;
            }
        }

        public void add(T t) {
            Node node = new Node<>(t);
            if (head == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }

        public void deleteHead() {
            if (head == null) {
                return;
            }
            head = head.next;
        }

        public void deleteTail() {
            if (head == null || tail == null) {
                return;
            }
            if (head == tail) {
                deleteHead();
                return;
            }
            Node current = head;
            while (current != null) {
                if (current.next == tail) {
                    current.next = null;
                    tail = current;
                    break;
                }
                current = current.next;
            }
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            Node node = head;
            while (node != null) {
                sb.append(node.t + "\n");
                node = node.next;
            }
            return sb.toString();
        }

        public void deleteN(int n) {
            int size = 0;
            Node current = head;
            while (current != null) {
                size++;
                current = current.next;
            }

            int index = size - n, i = 1;
            current = head;
            if (index == 0) {
                head = head.next;
                return;
            }
            while (i != index) {
                i++;
                current = current.next;
            }
            current.next = current.next.next;
        }
    }

    public static boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        int rem = 0, y = 0;
        int quo = x;
        while (quo != 0) {
            rem = quo % 10;
            y = y * 10 + rem;
            quo = quo / 10;
        }
        return y == x;
    }

    public static void main(String args[]) {
    }


    /**
     * 查找数组中最大的公共子串
     * 思路，获取第一个字符串的所有的子串，然后跟其他字符串进行比较，看看存在不存在
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        String first = strs[0];
        String maxSubString = "";
        long current = System.currentTimeMillis();
        for (int i = 0; i < first.length(); i++) {
            for (int k = 0; k < i + 1; k++) {
                String sub = first.substring(k, i + 1);
                //如果已经知道的子串比这个要比较的子串还长，那这个新的子串就没必要比较，直接break
                if (maxSubString.length() >= sub.length()) {
                    break;
                }
                for (int j = 1; j < strs.length; j++) {
                    if (strs[j].indexOf(sub) != -1) {
                        if (j == strs.length - 1) {
                            if (maxSubString.length() < sub.length()) {
                                maxSubString = sub;
                            }
                        }
                        continue;
                    } else {
                        break;
                    }
                }
            }
        }
        System.out.println("用时---->" + (System.currentTimeMillis() - current));
        return maxSubString;
    }


    public static int binarySearch(int[] array, int num) {
        int left = 0;
        int right = array.length;
        while (left < right) {
            int middle = (left + right) >> 1;
            System.out.println(left + "," + right + "," + middle);
            if (array[middle] == num) {
                return middle;
            } else if (array[middle] < num) {
                left = middle;
            } else {
                right = middle;
            }
        }
        return -1;
    }


    public static int[] getResult(int[] array, int result) {
        int[] results = null;

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            if (array[left] + array[right] == result) {
                results = new int[2];
                results[0] = left;
                results[1] = right;
                break;
            } else if (array[left] + array[right] < result) {
                left++;
            } else {
                right--;
            }
        }

        return results;
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
