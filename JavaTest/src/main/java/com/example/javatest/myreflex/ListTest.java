package com.example.javatest.myreflex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenYasheng
 * @date 2020/3/17
 * @Description
 */
public class ListTest {

    static final int MAXIMUM_CAPACITY = 1 << 30;
    public static void main(String[] args) {
        List<String> list=new ArrayList<>(100);

        System.out.println("list.size----->"+list.size());

        System.out.println(tableSizeFor(100));
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

}
