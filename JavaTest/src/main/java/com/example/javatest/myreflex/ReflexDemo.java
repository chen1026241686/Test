package com.example.javatest.myreflex;

import com.example.javatest.myreflex.annotation.AnnotationMe;
import com.example.javatest.myreflex.annotation.AnnotationTest;
import com.example.javatest.myreflex.model.Cat;
import com.example.javatest.myreflex.model.interfaces.Fly;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 1.继承父类里有private,public,static
 * 2.实现接口
 * 3.父类里面有内部类、静态内部类
 */
public class ReflexDemo<T> implements Comparable {


    Map<String, String> map;

    public static void main(String[] args) throws Exception {


        MyClassLoader myClassLoader = new MyClassLoader();

        System.out.println(ReflexDemo.class.getClassLoader().getClass().getName());


    }

    public <T> void testType(List<String> a1, List<ArrayList<String>> a2, List<T> a3, List<?> a4, List<ArrayList<String>[]> a5, Map<String, Integer> a6) {
    }


    public static void delete(List<?> a) {

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
