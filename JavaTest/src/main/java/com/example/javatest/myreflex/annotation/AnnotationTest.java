package com.example.javatest.myreflex.annotation;

import com.example.javatest.myreflex.TrafficLight;

/**
 * @author ChenYasheng
 * @date 2019/11/19
 * @Description
 */
@AnnotationMe(value = "Chen", color = "RED", scores = {15, 20},light = TrafficLight.GREEN)
public class AnnotationTest {
}
