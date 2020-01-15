package com.example.javatest.myreflex;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChenYasheng
 * @date 2020/1/6
 * @Description
 */
public class ReflectTest<T> {


    @Deprecated
    public Map<String, T> map = new HashMap<>();

    public Map<?, ?>[] arrayMap = new HashMap[10];


    public static void main(String[] args) throws Exception {


        Field fieldMap = ReflectTest.class.getField("map");
        Field fieldArrayMap = ReflectTest.class.getField("arrayMap");

        System.out.println("getDeclaringClass---->" + fieldMap.getDeclaringClass());
        System.out.println("getDeclaredAnnotations---->" + fieldMap.getDeclaredAnnotations());

        System.out.println("getModifiers---->" + fieldMap.getModifiers());
        System.out.println("getAnnotations---->" + fieldMap.getAnnotations());
        System.out.println("getAnnotatedType---->" + fieldMap.getAnnotatedType());

        System.out.println("getGenericType---->" + fieldMap.getGenericType());


        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (fieldMap.getGenericType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) fieldMap.getGenericType();
            System.out.println(parameterizedType.getRawType());
            System.out.println(parameterizedType.getRawType().getClass().getName());
            System.out.println(parameterizedType.getRawType().getTypeName());
            System.out.println(parameterizedType.getTypeName());

            for (int i = 0; i < parameterizedType.getActualTypeArguments().length; i++) {
                System.out.println(i + "--------->" + parameterizedType.getActualTypeArguments()[i].getTypeName());
            }

//            System.out.println(parameterizedType.getOwnerType().getTypeName());
        }

        System.out.println("-------------------------------------------------------------------------------------------------------");

        System.out.println(fieldArrayMap.getGenericType().getTypeName());
        System.out.println(fieldArrayMap.getGenericType() instanceof Type);
        System.out.println(fieldArrayMap.getGenericType() instanceof ParameterizedType);
        System.out.println(fieldArrayMap.getGenericType() instanceof GenericArrayType);
        System.out.println(fieldArrayMap.getGenericType() instanceof TypeVariable);
        System.out.println(fieldArrayMap.getGenericType() instanceof WildcardType);


    }
}
