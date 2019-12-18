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


    public static void main(String args[]) {
    }


}
