package com.example.javatest.myreflex;

/**
 * @author ChenYasheng
 * @date 2019/12/16
 * @Description 排序算法
 */
public class SortTest {
    public static void main(String[] args) {


        int[] arrays = new int[]{1, 3, 7, 4, 10, 5, 12, 2, -1, 20};

        System.out.println("--------------排序之前--------------");
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i] + ",");
        }
        System.out.println("\n");


        selectSort(arrays);


        System.out.println("--------------排序之后--------------");
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i] + ",");
        }
    }

    /**
     * 冒泡排序
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
     * 选择排序
     *
     * @param array
     */
    public static void selectSort(int array[]) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] > array[j]) {
                    array[i] = array[i] ^ array[j];
                    array[j] = array[i] ^ array[j];
                    array[i] = array[i] ^ array[j];
                }
            }
        }
    }

}
