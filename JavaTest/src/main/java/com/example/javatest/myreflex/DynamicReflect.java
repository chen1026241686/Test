package com.example.javatest.myreflex;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ChenYasheng
 * @date 2019/12/30
 * @Description
 */
public class DynamicReflect {


    public static void main(String[] args) {
        Collection collection = (Collection) getProxyObject(new ArrayList<>(), new Advice() {
            @Override
            public void beforeMethod() {
                System.out.println("方法执行之前");
            }

            @Override
            public void afterMethod() {
                System.out.println("方法执行之后");
            }
        });

        collection.add("A");
        collection.add("B");
        collection.add("C");
        System.out.println(collection.size());
    }

    public static Object getProxyObject(Object target, Advice advice) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (o, method, objects) -> {
            advice.beforeMethod();
            Object object = method.invoke(target, objects);
            advice.afterMethod();
            return object;
        });
    }
}
