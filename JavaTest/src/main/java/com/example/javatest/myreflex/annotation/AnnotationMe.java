package com.example.javatest.myreflex.annotation;

import com.example.javatest.myreflex.TrafficLight;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ChenYasheng
 * @date 2019/11/19
 * @Description
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AnnotationMe {
    String value();

    String color() default "Blue";

    int[] scores() default {100, 20, 120};

    TrafficLight light() default TrafficLight.RED;
}
