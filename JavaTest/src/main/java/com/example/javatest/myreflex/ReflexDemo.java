package com.example.javatest.myreflex;

import com.example.javatest.myreflex.model.Cat;
import com.example.javatest.myreflex.model.interfaces.Fly;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 1.继承父类里有private,public,static
 * 2.实现接口
 * 3.父类里面有内部类、静态内部类
 */
public class ReflexDemo {
    public static class Person {

        static {
            System.out.println("Person--->" + Person.class.getCanonicalName());
        }

        public void printAnimal() {


        }
    }

    public static void main(String args[]) {

        try {
        } catch (Exception e) {
            System.out.println("catchException----->" + e.toString());
        }


        Method[] methods = Cat.class.getMethods();




        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                System.out.println("CatMethod--->" + method.getName());
            }
        }





//        methods = Cat.Cat_2.class.getMethods();
//        if (methods != null && methods.length > 0) {
//            for (Method method : methods) {
//                System.out.println("Cat.Cat_2Method--->" + method.getName());
//            }
//        }

    }
}
